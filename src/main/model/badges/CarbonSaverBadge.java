package main.model.badges;

public class CarbonSaverBadge extends Badge {

    public CarbonSaverBadge() {
        super("Carbon Saver", "Used less than 30% of the weekly carbon budget.");
    }

    @Override
    public boolean checkCondition(double totalEmissions, int ecoScore) {
        return totalEmissions < 18.0;
    }
}
