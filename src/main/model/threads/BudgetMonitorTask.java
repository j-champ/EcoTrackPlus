package main.model.threads;

import main.model.budget.CarbonBudget;

public class BudgetMonitorTask implements Runnable {

    private final CarbonBudget budget;

    public BudgetMonitorTask(CarbonBudget budget) {
        this.budget = budget;
    }

    @Override
    public void run() {

        System.out.println("BudgetMonitorTask started on thread: "
                + Thread.currentThread().getName());

        while (!Thread.currentThread().isInterrupted()) {
            try {
                // Print current budget status
                String warning = budget.getWarningLevel();
                System.out.println("[Monitor] Budget Status: " + warning);
                Thread.sleep(3000);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        }

        System.out.println("BudgetMonitorTask stopped.");
    }
}
