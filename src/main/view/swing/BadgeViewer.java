package main.view.swing;

import main.model.badges.Badge;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BadgeViewer extends JFrame {

    public BadgeViewer(List<Badge> badges) {

        setTitle("Your Badges");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (badges.isEmpty()) {
            panel.add(new JLabel("No badges earned yet."));
        } else {
            for (Badge badge : badges) {

                JPanel row = new JPanel(new BorderLayout());
                row.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel name = new JLabel(badge.getName());
                name.setFont(new Font("Arial", Font.BOLD, 16));

                JLabel desc = new JLabel("<html>" + badge.getDescription() + "</html>");
                desc.setFont(new Font("Arial", Font.PLAIN, 14));

                row.add(name, BorderLayout.NORTH);
                row.add(desc, BorderLayout.CENTER);

                panel.add(row);
            }
        }

        JScrollPane scroll = new JScrollPane(panel);
        add(scroll);

        setVisible(true);
    }
}
