package main.model.activity;

import java.time.LocalDate;

public class RestoredActivity extends Activity {

    private final double storedEmission;

    public RestoredActivity(String category, String name,
                            LocalDate date, double storedEmission) {
        super(name, category, date);
        this.storedEmission = storedEmission;
    }

    @Override
    public double calculateEmission() {
        return storedEmission;
    }
}
