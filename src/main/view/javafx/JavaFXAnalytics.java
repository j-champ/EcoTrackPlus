package main.view.javafx;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.VBox;
import main.controller.DashboardController;
import main.model.activity.Activity;

import java.util.Map;

public class JavaFXAnalytics {

    private final DashboardController dashboardController;
    private final JFXPanel fxPanel;

    public JavaFXAnalytics(DashboardController controller) {
        this.dashboardController = controller;
        this.fxPanel = new JFXPanel();


        Platform.runLater(this::initFX);
    }

    public JFXPanel getFXPanel() {
        return fxPanel;
    }

    private void initFX() {
        VBox root = new VBox();
        root.setSpacing(20);

        PieChart pie = buildPieChart();
        BarChart<String, Number> bar = buildBarChart();
        LineChart<String, Number> line = buildLineChart();

        root.getChildren().addAll(pie, bar, line);

        Scene scene = new Scene(root, 700, 700);
        fxPanel.setScene(scene);
    }

    private PieChart buildPieChart() {
        PieChart pie = new PieChart();
        pie.setTitle("Emissions by Category");

        Map<String, Double> map = dashboardController.getEmissionByCategory();

        if (map.isEmpty()) {
            pie.getData().add(new PieChart.Data("No data", 1));
            return pie;
        }

        for (Map.Entry<String, Double> e : map.entrySet()) {
            pie.getData().add(new PieChart.Data(e.getKey(), e.getValue()));
        }

        return pie;
    }

    private BarChart<String, Number> buildBarChart() {

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Emissions");

        BarChart<String, Number> bar = new BarChart<>(xAxis, yAxis);
        bar.setTitle("Category Comparison");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Emissions");

        Map<String, Double> map = dashboardController.getEmissionByCategory();

        if (map.isEmpty()) {
            series.getData().add(new XYChart.Data<>("No data", 0));
        } else {
            for (Map.Entry<String, Double> e : map.entrySet()) {
                series.getData().add(new XYChart.Data<>(e.getKey(), e.getValue()));
            }
        }

        bar.getData().add(series);

        return bar;
    }

    private LineChart<String, Number> buildLineChart() {

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Entry");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Emission (kg)");

        LineChart<String, Number> line = new LineChart<>(xAxis, yAxis);
        line.setTitle("Activity Emissions Trend");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Emission per entry");

        int index = 1;
        for (Activity activity : dashboardController.getActivities()) {
            series.getData().add(
                    new XYChart.Data<>(String.valueOf(index++),
                            activity.calculateEmission()));
        }

        line.getData().add(series);

        return line;
    }
}

