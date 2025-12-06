package main.view.swing;

import main.controller.ActivityController;
import main.controller.DashboardController;
import main.model.badges.BadgeManager;
import main.model.budget.CarbonBudget;
import main.model.history.HistoryManager;
import main.model.score.EcoScoreEngine;
import main.model.threads.BudgetMonitorTask;
import main.model.threads.WeeklyResetThread;
import main.model.user.InvalidCredentialsException;
import main.model.user.User;
import main.model.user.UserManager;

import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    private final UserManager userManager;

    public LoginForm(UserManager userManager) {
        this.userManager = userManager;

        setTitle("EcoTrack+ Login");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(loginBtn); add(registerBtn);

        loginBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter username and password");
                return;
            }

            try {
                User user = userManager.login(username, password);
                JOptionPane.showMessageDialog(this, "Login successful!");

                launchUserSession(user.getUsername());
                dispose();

            } catch (InvalidCredentialsException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Login error: " + ex.getMessage());
            }
        });

        registerBtn.addActionListener(e ->
                new RegisterForm(userManager)
        );
    }

    private void launchUserSession(String username) {

        CarbonBudget budget = new CarbonBudget(60);
        EcoScoreEngine scoreEngine = new EcoScoreEngine();
        HistoryManager historyManager = new HistoryManager();
        BadgeManager badgeManager = new BadgeManager();

        // Restore from user files
        userManager.loadProfile(username, scoreEngine, budget);

        ActivityController activityController =
                new ActivityController(budget, scoreEngine, historyManager,
                        badgeManager, userManager, username);

        // Load past activities and badges
        userManager.loadActivities(username, activityController);
        userManager.loadBadges(username, badgeManager);

        DashboardController dashboardController =
                new DashboardController(activityController, scoreEngine, budget);

        // Start monitor + weekly reset threads
        BudgetMonitorTask monitorTask = new BudgetMonitorTask(budget);
        Thread monitorThread = new Thread(monitorTask, "BudgetMonitorThread");
        monitorThread.start();

        WeeklyResetThread resetThread =
                new WeeklyResetThread(budget, scoreEngine, historyManager);
        resetThread.start();

        SwingUtilities.invokeLater(() -> {
            SwingDashboard ui = new SwingDashboard(activityController, dashboardController);

            ui.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    monitorThread.interrupt();
                    resetThread.interrupt();
                    System.out.println("Shutting down threads...");
                }
            });
        });
    }
}
