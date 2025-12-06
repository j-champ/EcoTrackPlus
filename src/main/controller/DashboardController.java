package main.controller;

import main.model.activity.Activity;
import main.model.budget.CarbonBudget;
import main.model.score.EcoScoreEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardController {

    private final ActivityController activityController;
    private final EcoScoreEngine scoreEngine;
    private final CarbonBudget budget;

    public DashboardController(ActivityController activityController,
                               EcoScoreEngine scoreEngine,
                               CarbonBudget budget) {
        this.activityController = activityController;
        this.scoreEngine = scoreEngine;
        this.budget = budget;
    }

    public List<Activity> getActivities() {
        return activityController.getAllActivities();
    }


    public double getRemainingBudgetPercentage() {
        double max = budget.getWeeklyLimit();
        if (max <= 0) return 0;
        return (budget.getRemainingBudget() / max) * 100.0;
    }

    public double getTotalEmissions() {
        return activityController.getAllActivities()
                .stream()
                .mapToDouble(Activity::calculateEmission)
                .sum();
    }

    public String getBudgetWarning() {
        return budget.getWarningLevel();
    }


    public int getEcoScore() {
        return scoreEngine.getScore();
    }

    public Map<String, Double> getEmissionByCategory() {
        Map<String, Double> map = new HashMap<>();

        for (Activity a : activityController.getAllActivities()) {
            String name = a.getName();
            double emission = a.calculateEmission();

            map.put(name, map.getOrDefault(name, 0.0) + emission);
        }

        return map;
    }
}
