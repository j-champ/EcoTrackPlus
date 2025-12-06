package main;

import main.model.user.UserManager;
import main.view.swing.LoginForm;

import javax.swing.*;

public class MainApp {

    public static void main(String[] args) {

        new javafx.embed.swing.JFXPanel();
        javafx.application.Platform.setImplicitExit(false);

        UserManager userManager = new UserManager();

        SwingUtilities.invokeLater(() ->
                new LoginForm(userManager)
        );
    }
}
