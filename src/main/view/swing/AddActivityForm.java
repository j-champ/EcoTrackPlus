package main.view.swing;

import main.controller.ActivityController;
import main.model.activity.FoodActivity;
import main.model.activity.TravelActivity;
import main.model.activity.EnergyActivity;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddActivityForm extends JFrame {

    private final ActivityController controller;
    private final Runnable refreshCallback;

    public AddActivityForm(ActivityController controller,
                           Runnable refreshCallback) {

        this.controller = controller;
        this.refreshCallback = refreshCallback;

        setTitle("Add Activity");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel typeLabel = new JLabel("Activity Type:");
        String[] types = {"Travel", "Food", "Energy"};
        JComboBox<String> typeBox = new JComboBox<>(types);

        JLabel optionLabel = new JLabel("Option:");
        JComboBox<String> optionBox = new JComboBox<>();

        JLabel qtyLabel = new JLabel("Quantity:");
        JTextField qtyField = new JTextField();

        JButton submitBtn = new JButton("Submit");

        optionBox.setModel(new DefaultComboBoxModel<>(
                new String[]{"Car", "Bus", "Bike", "Train", "Walk"}
        ));

        typeBox.addActionListener(e -> {
            String selected = (String) typeBox.getSelectedItem();

            if (selected.equals("Travel")) {
                optionBox.setModel(new DefaultComboBoxModel<>(
                        new String[]{"Car", "Bus", "Bike", "Train", "Walk"}
                ));
            }
            else if (selected.equals("Food")) {
                optionBox.setModel(new DefaultComboBoxModel<>(
                        new String[]{"Veg", "Non-Veg", "Vegan"}
                ));
            }
            else {
                optionBox.setModel(new DefaultComboBoxModel<>(
                        new String[]{"Electricity (kWh)"}
                ));
            }
        });


        add(typeLabel); add(typeBox);
        add(optionLabel); add(optionBox);
        add(qtyLabel); add(qtyField);
        add(new JLabel()); add(submitBtn);

        submitBtn.addActionListener(e -> {
            try {
                String type = (String) typeBox.getSelectedItem();
                String option = (String) optionBox.getSelectedItem();
                double qty = Double.parseDouble(qtyField.getText().trim());
                LocalDate date = LocalDate.now();

                String result;

                switch (type) {
                    case "Travel" -> result =
                            controller.addActivity(new TravelActivity(option, qty, date));

                    case "Food" -> result =
                            controller.addActivity(new FoodActivity(option, qty, date));

                    default -> result = controller.addActivity(
                            new EnergyActivity(qty, date)
                    );
                }

                JOptionPane.showMessageDialog(this, result);

                if (refreshCallback != null)
                    refreshCallback.run();

                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid number for quantity.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage());
            }
        });

        setVisible(true);
    }
}
