package main.model.budget;

public class CarbonBudget {

    private final double weeklyLimit;
    private double remainingBudget;
    private String warningLevel = "High";

    public CarbonBudget(double weeklyLimit) {
        if (weeklyLimit <= 0) {
            throw new IllegalArgumentException("Weekly limit must be positive!");
        }
        this.weeklyLimit = weeklyLimit;
        this.remainingBudget = weeklyLimit;
        updateWarningLevel();
    }

    public synchronized void deduct(double amount) throws BudgetExceededException {
        if (amount < 0) {
            throw new IllegalArgumentException("Emission cannot be negative!");
        }
        if (amount > remainingBudget) {
            throw new BudgetExceededException("Carbon budget exceeded!");
        }
        remainingBudget -= amount;
        if (remainingBudget < 0) remainingBudget = 0;
        updateWarningLevel();
    }

    public synchronized double getRemainingBudget() {
        return remainingBudget;
    }

    public double getWeeklyLimit() {
        return weeklyLimit;
    }

    public synchronized String getWarningLevel() {
        return warningLevel;
    }

    public synchronized void resetBudget() {
        remainingBudget = weeklyLimit;
        updateWarningLevel();
    }

    public synchronized void setRemainingBudget(double remainingBudget) {
        this.remainingBudget = Math.max(0, Math.min(remainingBudget, weeklyLimit));
        updateWarningLevel();
    }

    private void updateWarningLevel() {
        double pct = (remainingBudget / weeklyLimit) * 100.0;
        if (pct < 20)      warningLevel = "Low";
        else if (pct < 50) warningLevel = "Medium";
        else               warningLevel = "High";
    }
}
