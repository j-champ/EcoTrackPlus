package main.view.swing;

import main.controller.ActivityController;
import main.controller.DashboardController;
import main.model.activity.Activity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SwingDashboard extends JFrame {

    private final ActivityController activityController;
    private final DashboardController dashboardController;

    private JTable table;
    private DefaultTableModel tableModel;
    private JProgressBar budgetBar;
    private JLabel scoreLabel;
    private JLabel warningLabel;

    public SwingDashboard(ActivityController activityController,
                          DashboardController dashboardController) {

        this.activityController = activityController;
        this.dashboardController = dashboardController;

        setTitle("EcoTrack+ Dashboard");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        budgetBar = new JProgressBar(0, 100);
        budgetBar.setStringPainted(true);

        scoreLabel = new JLabel("EcoScore: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));

        warningLabel = new JLabel("", SwingConstants.CENTER);
        warningLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        topPanel.add(budgetBar);
        topPanel.add(scoreLabel);
        topPanel.add(warningLabel);

        tableModel = new DefaultTableModel(new String[]{"Activity", "Date", "Emission"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addBtn = new JButton("Add Activity");
        JButton chartBtn = new JButton("View Analytics");
        JButton badgesBtn = new JButton("View Badges");

        addBtn.addActionListener(e ->
                new AddActivityForm(activityController, this::refreshDashboard)
        );

        chartBtn.addActionListener(e -> new SwingFXPanelBridge(dashboardController));

        badgesBtn.addActionListener(e ->
                new BadgeViewer(activityController.getEarnedBadges())
        );

        bottomPanel.add(addBtn);
        bottomPanel.add(chartBtn);
        bottomPanel.add(badgesBtn);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        Timer autoRefresh = new Timer(1500, e -> refreshDashboard());
        autoRefresh.setRepeats(true);
        autoRefresh.start();

        refreshDashboard();
        setVisible(true);
    }

    private void refreshDashboard() {
        SwingUtilities.invokeLater(() -> {
            loadActivityTable();

            double pct = dashboardController.getRemainingBudgetPercentage();
            budgetBar.setValue((int) pct);
            budgetBar.setString(String.format("Budget Remaining: %.1f%%", pct));

            scoreLabel.setText("EcoScore: " + dashboardController.getEcoScore());
            warningLabel.setText("Budget Status: " + dashboardController.getBudgetWarning());
        });
    }

    private void loadActivityTable() {
        List<Activity> list = dashboardController.getActivities();
        tableModel.setRowCount(0);

        for (Activity a : list) {
            tableModel.addRow(new Object[]{
                    a.getName(),
                    a.getDate(),
                    String.format("%.2f", a.calculateEmission())
            });
        }
    }
}
