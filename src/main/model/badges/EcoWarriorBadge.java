package main.model.badges;

public class EcoWarriorBadge extends Badge {

    public EcoWarriorBadge() {
        super("Eco Warrior", "Achieved an EcoScore of 80 or more.");
    }

    @Override
    public boolean checkCondition(double totalEmissions, int ecoScore) {
        return ecoScore >= 80;
    }
}
