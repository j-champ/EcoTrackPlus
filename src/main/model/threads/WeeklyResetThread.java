package main.model.threads;

import main.model.budget.CarbonBudget;
import main.model.history.HistoryManager;
import main.model.score.EcoScoreEngine;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class WeeklyResetThread extends Thread {

    private final CarbonBudget budget;
    private final EcoScoreEngine scoreEngine;
    private final HistoryManager historyManager;
    private volatile boolean running = true;

    public WeeklyResetThread(CarbonBudget budget,
                             EcoScoreEngine scoreEngine,
                             HistoryManager historyManager) {
        this.budget = budget;
        this.scoreEngine = scoreEngine;
        this.historyManager = historyManager;
        setName("WeeklyResetThread");
        setDaemon(true);
    }

    @Override
    public void run() {
        LocalDate lastReset = null;

        while (!isInterrupted() && running) {
            try {
                Thread.sleep(60 * 60 * 1000L);

                LocalDate today = LocalDate.now();
                if (today.getDayOfWeek() == DayOfWeek.MONDAY &&
                        (lastReset == null || !today.equals(lastReset))) {

                    try {
                        historyManager.saveWeeklySummary(
                                budget.getRemainingBudget(),
                                scoreEngine.getScore()
                        );
                    } catch (Exception ignored) {
                        // If not supported or fails, just continue
                    }

                    budget.resetBudget();
                    scoreEngine.resetScoreWeekly();
                    lastReset = today;

                    System.out.println("✅ Weekly reset completed.");
                }

            } catch (InterruptedException e) {
                interrupt();
                break;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("WeeklyResetThread stopped.");
    }

    public void stopThread() {
        running = false;
        interrupt();
    }
}
