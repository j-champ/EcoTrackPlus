package main.view.swing;

import main.model.user.UserAlreadyExistsException;
import main.model.user.UserManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RegisterForm extends JFrame {

    private final UserManager userManager;

    public RegisterForm(UserManager userManager) {
        this.userManager = userManager;

        setTitle("Register New User");
        setSize(350, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        setVisible(true);
    }

    private void initUI() {
        setLayout(new GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel confirmLabel = new JLabel("Confirm Password:");

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();

        JButton registerBtn = new JButton("Register");

        add(userLabel); add(userField);
        add(passLabel); add(passField);
        add(confirmLabel); add(confirmField);
        add(new JLabel()); add(registerBtn);

        registerBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            String confirm = new String(confirmField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fill all fields.");
                return;
            }
            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            try {
                userManager.registerUser(username, password);
                JOptionPane.showMessageDialog(this, "Registered successfully!");
                dispose();
            } catch (UserAlreadyExistsException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error while registering: " + ex.getMessage());
            }
        });
    }
}
