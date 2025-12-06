package main.model.history;

import main.model.activity.Activity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class HistoryManager {

    private static final String BASE_DIR =
            System.getProperty("user.home") + File.separator + "EcoTrackPlus" + File.separator;
    private static final String HISTORY_FILE = BASE_DIR + "activity_history.txt";
    private static final String WEEKLY_FILE  = BASE_DIR + "weekly_summary.txt";

    static {
        File dir = new File(BASE_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    public static class WeeklySummary {
        private final double remainingBudget;
        private final int ecoScore;

        public WeeklySummary(double remainingBudget, int ecoScore) {
            this.remainingBudget = remainingBudget;
            this.ecoScore = ecoScore;
        }

        @Override
        public String toString() {
            return "RemainingBudget=" + remainingBudget +
                    ", EcoScore=" + ecoScore;
        }
    }

    public class HistoryReader {
        public String readHistory() {
            try {
                return Files.readString(Path.of(HISTORY_FILE));
            } catch (IOException e) {
                return "Could not read history file.";
            }
        }
    }

    public void saveActivity(Activity activity) {
        try (FileWriter fw = new FileWriter(HISTORY_FILE, true)) {
            fw.write(activity.toString());
            fw.write(System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Error saving activity: " + e.getMessage());
        }
    }
    public void saveWeeklySummary(double remainingBudget, int ecoScore) {

        WeeklySummary summary = new WeeklySummary(remainingBudget, ecoScore);

        try (FileWriter fw = new FileWriter(WEEKLY_FILE, true)) {
            fw.write("WEEK END — " + LocalDate.now() + System.lineSeparator());
            fw.write(summary.toString() + System.lineSeparator());
            fw.write(System.lineSeparator());
        }
        catch (IOException e) {
            System.out.println("Error saving weekly summary: " + e.getMessage());
        }
    }
}
