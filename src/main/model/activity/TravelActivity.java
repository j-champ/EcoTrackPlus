package main.model.activity;

import java.time.LocalDate;

public class TravelActivity extends Activity {

    private final double distanceKm;

    public TravelActivity(String mode, double distanceKm, LocalDate date) {
        super(mode, "Travel", date);
        this.distanceKm = distanceKm;
    }

    @Override
    public double calculateEmission() {
        switch (name.toLowerCase()) {
            case "car":   return distanceKm * 0.21;
            case "bus":   return distanceKm * 0.10;
            case "bike":  return 0;
            case "train": return distanceKm * 0.05;
            case "walk":  return 0;
            default:      return 0;
        }
    }
}
