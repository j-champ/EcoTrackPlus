package main.model.activity;

import java.time.LocalDate;

public abstract class Activity {

    protected String name;
    protected String category;
    protected LocalDate date;

    public Activity(String name, String category, LocalDate date) {
        this.name = name;
        this.category = category;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    public abstract double calculateEmission();

    @Override
    public String toString() {
        return category + "," + name + "," + date + "," + calculateEmission();
    }
}
