package main.controller;

import main.model.activity.Activity;
import main.model.budget.BudgetExceededException;
import main.model.budget.CarbonBudget;
import main.model.history.HistoryManager;
import main.model.score.EcoScoreEngine;
import main.model.badges.Badge;
import main.model.badges.BadgeManager;
import main.model.user.UserManager;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {

    private final List<Activity> activityList;
    private final CarbonBudget carbonBudget;
    private final EcoScoreEngine scoreEngine;
    private final HistoryManager historyManager;
    private final BadgeManager badgeManager;
    private final UserManager userManager;
    private final String username;

    public ActivityController(CarbonBudget carbonBudget,
                              EcoScoreEngine scoreEngine,
                              HistoryManager historyManager,
                              BadgeManager badgeManager,
                              UserManager userManager,
                              String username) {

        this.activityList = new ArrayList<>();
        this.carbonBudget = carbonBudget;
        this.scoreEngine = scoreEngine;
        this.historyManager = historyManager;
        this.badgeManager = badgeManager;
        this.userManager = userManager;
        this.username = username;
    }

    public String addActivity(Activity activity) {
        double emission = activity.calculateEmission();

        try {
            carbonBudget.deduct(emission);
        } catch (BudgetExceededException ex) {
            return "Error: " + ex.getMessage();
        }

        activityList.add(activity);
        scoreEngine.updateScore(emission);

        // Optional global history
        historyManager.saveActivity(activity);

        // Per-user history + profile + badges
        userManager.saveActivity(username, activity);
        userManager.saveProfile(username, scoreEngine, carbonBudget);

        double totalEmissions = activityList.stream()
                .mapToDouble(Activity::calculateEmission)
                .sum();
        badgeManager.checkBadges(totalEmissions, scoreEngine);
        userManager.saveBadges(username, badgeManager);

        return "Activity added successfully!";
    }

    public void loadActivity(Activity activity) {
        activityList.add(activity);
    }

    public List<Badge> getEarnedBadges() {
        return badgeManager.getEarnedBadges();
    }

    public List<Activity> getAllActivities() {
        return new ArrayList<>(activityList);
    }

    public double getRemainingBudget() {
        return carbonBudget.getRemainingBudget();
    }

    public int getEcoScore() {
        return scoreEngine.getScore();
    }

    public String getBudgetWarningLevel() {
        return carbonBudget.getWarningLevel();
    }
}
