package main.view.swing;

import main.controller.DashboardController;
import main.view.javafx.JavaFXAnalytics;

import javax.swing.*;
import java.awt.*;

public class SwingFXPanelBridge extends JFrame {

    private final DashboardController controller;

    public SwingFXPanelBridge(DashboardController controller) {
        this.controller = controller;
        setTitle("Analytics");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JavaFXAnalytics analytics = new JavaFXAnalytics(controller);
        JComponent fxPanel = analytics.getFXPanel();

        setLayout(new BorderLayout());
        add(fxPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
