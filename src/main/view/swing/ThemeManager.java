package main.view.swing;

import javax.swing.*;

public class ThemeManager {

    public static void applySystemTheme() {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("Theme error: " + e.getMessage());
        }
    }

    public static void applyDarkTheme() {
        try {
            UIManager.setLookAndFeel(
                    "javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.out.println("Dark theme error.");
        }
    }
}
