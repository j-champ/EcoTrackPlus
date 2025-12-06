package main.model.badges;

public class GreenBeginnerBadge extends Badge {

    public GreenBeginnerBadge() {
        super("Green Beginner", "Completed your first eco-friendly activity.");
    }

    @Override
    public boolean checkCondition(double totalEmissions, int ecoScore) {
        return ecoScore > 0;
    }
}
