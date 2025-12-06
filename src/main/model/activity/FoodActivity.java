package main.model.activity;

import java.time.LocalDate;

public class FoodActivity extends Activity {

    private final double meals;

    public FoodActivity(String type, double meals, LocalDate date) {
        super(type, "Food", date);
        this.meals = meals;
    }

    @Override
    public double calculateEmission() {
        switch (name.toLowerCase()) {
            case "veg":
                return meals * 1.5;
            case "non-veg":
                return meals * 3.5;
            case "vegan":
                return meals * 1.0;
            default:
                return 0;
        }
    }
}
