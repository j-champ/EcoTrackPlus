package main.model.badges;

import main.model.score.EcoScoreEngine;

import java.util.ArrayList;
import java.util.List;

public class BadgeManager {

    private final List<Badge> earnedBadges;

    public BadgeManager() {
        this.earnedBadges = new ArrayList<>();
    }

    public void checkBadges(double totalEmissions, EcoScoreEngine scoreEngine) {
        int score = scoreEngine.getScore();

        List<Badge> allBadges = List.of(
                new GreenBeginnerBadge(),
                new EcoWarriorBadge(),
                new CarbonSaverBadge()
        );

        for (Badge badge : allBadges) {
            if (!hasBadge(badge.getName()) &&
                    badge.checkCondition(totalEmissions, score)) {

                badge.earn();
                earnedBadges.add(badge);
            }
        }
    }

    private boolean hasBadge(String name) {
        return earnedBadges.stream()
                .anyMatch(b -> b.getName().equalsIgnoreCase(name));
    }

    public List<Badge> getEarnedBadges() {
        return new ArrayList<>(earnedBadges);
    }

    public void forceEarn(String badgeName) {
        if (hasBadge(badgeName)) return;

        Badge b = null;

        if (badgeName.equalsIgnoreCase("Green Beginner")) {
            b = new GreenBeginnerBadge();
        } else if (badgeName.equalsIgnoreCase("Eco Warrior")) {
            b = new EcoWarriorBadge();
        } else if (badgeName.equalsIgnoreCase("Carbon Saver")) {
            b = new CarbonSaverBadge();
        }

        if (b != null) {
            b.earn();
            earnedBadges.add(b);
        }
    }
}
