package main.model.activity;

import java.time.LocalDate;

public class EnergyActivity extends Activity {

    private final double kWh;

    public EnergyActivity(double kWh, LocalDate date) {
        super("Electricity", "Energy", date);
        this.kWh = kWh;
    }

    @Override
    public double calculateEmission() {
        return kWh * 0.82;
    }
}
