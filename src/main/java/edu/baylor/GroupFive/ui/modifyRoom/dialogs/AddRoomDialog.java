package edu.baylor.GroupFive.ui.modifyRoom.dialogs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import edu.baylor.GroupFive.database.controllers.RoomController;
import edu.baylor.GroupFive.models.Room;
import edu.baylor.GroupFive.models.enums.Theme;
import edu.baylor.GroupFive.models.enums.BedType;
import edu.baylor.GroupFive.models.enums.Quality;

public class AddRoomDialog extends JDialog {

    private JTable table;

    public AddRoomDialog(JTable owner) {
        super(javax.swing.SwingUtilities.windowForComponent(owner));
        table = owner;
        createGUI();
    }

    private void createGUI() {
        // Sets up dialog panel
        setPreferredSize(new Dimension(600, 400));
        setTitle("Add new room");

        // Sets up list
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.Y_AXIS));

        if (table == null) {
            JOptionPane.showMessageDialog(this, "Table is null");
            return;
        }

        List<JLabel> labels = new ArrayList<>();

        // Create labels for each field
        JLabel roomNumberLabel = new JLabel("Room Number:");
        JLabel roomTypeLabel = new JLabel("Room Type:");
        JLabel qualityLabel = new JLabel("Quality:");
        JLabel bedTypeLabel = new JLabel("Bed Type:");
        JLabel bedCountLabel = new JLabel("Bed Count:");
        JLabel smoking = new JLabel("Smoking:");
        JLabel priceLabel = new JLabel("Price per Night:");

        // Add labels to list
        labels.add(roomNumberLabel);
        labels.add(roomTypeLabel);
        labels.add(qualityLabel);
        labels.add(bedTypeLabel);
        labels.add(bedCountLabel);
        labels.add(smoking);
        labels.add(priceLabel);

        // Create textfields for each field
        JTextField roomNumber = new JTextField();
        JComboBox<String> roomType = new JComboBox<>(new String[] { "NatureRetreat", "UrbanElegance", "VintageCharm"});
        JComboBox<String> quality = new JComboBox<>(new String[] { "Economy", "Comfort", "Busniess", "Executive" });
        JComboBox<String> bedType = new JComboBox<>(new String[] { "Single", "Double", "Queen", "King" });
        JTextField bedCount = new JTextField();
        JComboBox<String> smokingBox = new JComboBox<>(new String[] { "true", "false" });
        JTextField price = new JTextField();

        // Set default textfield sizes
        roomNumber.setPreferredSize(new Dimension(200, 30));
        bedCount.setPreferredSize(new Dimension(200, 30));
        price.setPreferredSize(new Dimension(200, 30));

        // Add textfields to list
        List<Component> textFields = new ArrayList<>();
        textFields.add(roomNumber);
        textFields.add(roomType);
        textFields.add(quality);
        textFields.add(bedType);
        textFields.add(bedCount);
        textFields.add(smokingBox);
        textFields.add(price);

        // Make a new panel for each text field and label and add them
        for (int i = 0; i < 7; i++) {
            JPanel panel = new JPanel();
            panel.add(labels.get(i));
            panel.add(textFields.get(i));
            listPane.add(panel);
        }

        // Makes add row button
        JButton addButton = new JButton("Add room");
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check that all fields are filled
                if (roomNumber.getText().isEmpty() || bedCount.getText().isEmpty() || price.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(table, "Please fill out all fields.");
                    return;
                }

                // Check that room number is a number
                try {
                    Integer.parseInt(roomNumber.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(table, "Room number must be a number.");
                    return;
                }

                // Check that room number isnt already in database
                if (RoomController.getRoomInfo(Integer.parseInt(roomNumber.getText())) != null) {
                    JOptionPane.showMessageDialog(table, "Room number already exists.");
                    return;
                }

                // Check that bed count is a number
                try {
                    Integer.parseInt(bedCount.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(table, "Bed count must be a number.");
                    return;
                }

                // Check that price is a number
                try {
                    Double.parseDouble(price.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(table, "Price must be a number.");
                    return;
                }

                // Add room to database
                Room room = new Room(Integer.parseInt(roomNumber.getText()), Quality.fromString((String) quality.getSelectedItem()),
                        Theme.valueOf((String) roomType.getSelectedItem()), Boolean.parseBoolean((String) smokingBox.getSelectedItem()), Integer.parseInt(bedCount.getText()),
                        BedType.valueOf(((String) bedType.getSelectedItem()).toUpperCase()), Double.parseDouble(price.getText()));
                
                Boolean valid = RoomController.addRoom(room);

                if (!valid) {
                    JOptionPane.showMessageDialog(table, "Failed to add room to database.");
                    return;
                }

                // Add new row to table
                Object[] row = new Object[] { Integer.parseInt(roomNumber.getText()), roomType.getSelectedItem(), ((String) quality.getSelectedItem()).toLowerCase(),
                    ((String) bedType.getSelectedItem()).toLowerCase(), bedCount.getText(), smokingBox.getSelectedItem(), price.getText() };
                    ((javax.swing.table.DefaultTableModel) table.getModel()).addRow(row);
                
                dispose();
                JOptionPane.showMessageDialog(table, "Room added successfully.");
            }
        });
        listPane.add(addButton);
        add(listPane);
        pack();
        setLocationRelativeTo(getParent());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
