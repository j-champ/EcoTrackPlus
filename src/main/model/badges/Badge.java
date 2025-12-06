package main.model.badges;

public abstract class Badge {

    protected final String name;
    protected final String description;
    protected boolean earned = false;

    public Badge(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void earn() {
        this.earned = true;
    }

    public abstract boolean checkCondition(double totalEmissions, int ecoScore);
}
