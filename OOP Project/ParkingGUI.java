import java.awt.*;
import javax.swing.*;

public class ParkingGUI extends JFrame {
    private ParkingPlaza plaza;
    private JButton[] slotButtons;
    private JLabel totalSlotsLabel, occupiedLabel, availableLabel, revenueLabel;
    
    private static final Color AVAILABLE_COLOR = new Color(0, 200, 0);
    private static final Color OCCUPIED_COLOR = new Color(255, 0, 0);
    private static final Color TEXT_COLOR = Color.WHITE;
    
    public ParkingGUI(ParkingPlaza plaza) {
        this.plaza = plaza;
        setTitle("Parking Lot Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initializeGUI();
        setVisible(true);
    }
    
    private void initializeGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(createHeaderPanel(), BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(new JScrollPane(createSlotsPanel()), BorderLayout.CENTER);
        centerPanel.add(createStatsPanel(), BorderLayout.EAST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        JPanel footerPanel = new JPanel();
        JButton exitButton = new JButton("Exit Application");
        exitButton.addActionListener(e -> System.exit(0));
        footerPanel.add(exitButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(51, 51, 51));
        JLabel titleLabel = new JLabel("Welcome to Smart Parking Lot Management");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel);
        return panel;
    }
    
    private JPanel createSlotsPanel() {
        int totalSlots = plaza.getTotalSlots();
        int gridCols = 5;
        int gridRows = (int) Math.ceil((double) totalSlots / gridCols);
        JPanel panel = new JPanel(new GridLayout(gridRows, gridCols, 10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createTitledBorder("Parking Slots"));
        
        slotButtons = new JButton[totalSlots];
        ParkingSlot[] allSlots = plaza.getAllSlots();
        for (int i = 0; i < totalSlots; i++) {
            ParkingSlot slot = allSlots[i];
            JButton btn = new JButton();
            updateSlotButton(btn, slot);
            btn.addActionListener(e -> handleSlotClick(slot));
            slotButtons[i] = btn;
            panel.add(btn);
        }
        return panel;
    }
    
    private void updateSlotButton(JButton btn, ParkingSlot slot) {
        btn.setText("(" + slot.slotID + ")\n" + slot.getSlotType());
        btn.setFont(new Font("Arial", Font.BOLD, 10));
        btn.setFocusPainted(false);
        btn.setBackground(slot.isOccupied ? OCCUPIED_COLOR : AVAILABLE_COLOR);
        btn.setForeground(TEXT_COLOR);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setPreferredSize(new Dimension(120, 120));
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Live Statistics"));
        panel.setBackground(new Color(230, 230, 250));
        panel.setPreferredSize(new Dimension(200, 0));
        totalSlotsLabel = createStatLabel("Total Slots: 0");
        occupiedLabel = createStatLabel("Occupied: 0");
        availableLabel = createStatLabel("Available: 0");
        revenueLabel = createStatLabel("Revenue: $0.00");
        panel.add(Box.createVerticalStrut(10));
        panel.add(totalSlotsLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(occupiedLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(availableLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(revenueLabel);
        panel.add(Box.createVerticalGlue());
        updateStatistics();
        return panel;
    }
    
    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    private void handleSlotClick(ParkingSlot slot) {
        if (slot.isOccupied) showExitDialog(slot);
        else showParkingDialog(slot);
    }
    
    private void showParkingDialog(ParkingSlot slot) {
        JDialog dialog = new JDialog(this, "Park Vehicle in Slot " + slot.slotID, true);
        dialog.setSize(450, 320);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel("Slot ID: " + slot.slotID));
        panel.add(new JLabel("Slot Type: " + slot.getSlotType()));
        panel.add(new JLabel("Base Rate: $" + String.format("%.2f", slot.baseRate) + "/hour"));
        panel.add(Box.createVerticalStrut(15));
        panel.add(new JLabel("Select Vehicle Type:"));
        panel.add(Box.createVerticalStrut(5));
        String[] vehicleTypes = {"COMPACT", "LARGE", "EV"};
        JComboBox<String> vehicleCombo = new JComboBox<>(vehicleTypes);
        panel.add(vehicleCombo);
        panel.add(Box.createVerticalStrut(15));
        panel.add(new JLabel("Hours to Park (1-24):"));
        panel.add(Box.createVerticalStrut(5));
        SpinnerModel hoursModel = new SpinnerNumberModel(1, 1, 24, 1);
        JSpinner hoursSpinner = new JSpinner(hoursModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(hoursSpinner, "#");
        hoursSpinner.setEditor(editor);
        JFormattedTextField textField = editor.getTextField();
        textField.setFocusLostBehavior(JFormattedTextField.COMMIT);
        textField.addActionListener(e -> {
            try {
                hoursSpinner.commitEdit();
                int value = (Integer) hoursSpinner.getValue();
                if (value < 1 || value > 24) {
                    hoursSpinner.setValue(1);
                    JOptionPane.showMessageDialog(dialog, "Hours must be between 1 and 24!\nValue reset to 1.", "Invalid Hours", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                hoursSpinner.setValue(1);
                JOptionPane.showMessageDialog(dialog, "Invalid number! Value reset to 1.", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(hoursSpinner);
        panel.add(Box.createVerticalGlue());
        JPanel btnPanel = new JPanel();
        JButton confirmButton = new JButton("Park Vehicle");
        JButton cancelButton = new JButton("Cancel");
        confirmButton.addActionListener(e -> {
            try {
                hoursSpinner.commitEdit();
                String vehicleType = (String) vehicleCombo.getSelectedItem();
                Object value = hoursSpinner.getValue();
                
                if (!(value instanceof Integer)) {
                    throw new IllegalArgumentException("Invalid hours input - not an integer");
                }
                
                int hours = (Integer) value;
                if (hours < 1 || hours > 24) {
                    throw new IllegalArgumentException("Hours must be between 1 and 24, received: " + hours);
                }
                
                if (!slot.getSlotType().equals(vehicleType)) {
                    JOptionPane.showMessageDialog(dialog, "The selected slot is reserved for " + slot.getSlotType() + " vehicles.\nThus, you can't park here.\nPlease select another free spot.", "Slot Type Mismatch", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                slot.isOccupied = true;
                slot.vehicleType = vehicleType;
                slot.hoursParked = hours;
                refreshDisplay();
                JOptionPane.showMessageDialog(dialog, "Vehicle parked successfully!\nVehicle Type: " + vehicleType + "\nHours Booked: " + hours + " hours", "Success", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, "Parking failed: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        });
        cancelButton.addActionListener(e -> dialog.dispose());
        btnPanel.add(confirmButton);
        btnPanel.add(Box.createHorizontalStrut(10));
        btnPanel.add(cancelButton);
        panel.add(btnPanel);
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    // POLYMORPHISM: Calls calculateTotalFee() - behavior depends on slot type
    private void showExitDialog(ParkingSlot slot) {
        JDialog dialog = new JDialog(this, "Exit Vehicle from Slot " + slot.slotID, true);
        dialog.setSize(450, 320);
        dialog.setLocationRelativeTo(this);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.add(new JLabel("Slot ID: " + slot.slotID));
        panel.add(new JLabel("Slot Type: " + slot.getSlotType()));
        panel.add(new JLabel("Vehicle Type Parked: " + slot.vehicleType));
        panel.add(Box.createVerticalStrut(15));
        int hoursParked = slot.hoursParked > 0 ? slot.hoursParked : 1;
        panel.add(new JLabel("Hours Parked: " + hoursParked + " hour(s)"));
        panel.add(Box.createVerticalStrut(15));
        try {
            double totalFee = slot.calculateTotalFee(hoursParked);
            JLabel feeLabel = new JLabel("Total Amount Due: $" + String.format("%.2f", totalFee));
            feeLabel.setFont(new Font("Arial", Font.BOLD, 16));
            feeLabel.setForeground(new Color(0, 100, 200));
            panel.add(feeLabel);
            panel.add(Box.createVerticalGlue());
            JPanel btnPanel = new JPanel();
            JButton exitButton = new JButton("Exit & Pay");
            JButton cancelButton = new JButton("Cancel");
            exitButton.addActionListener(e -> {
                try {
                    plaza.exitVehicle(slot.slotID, hoursParked);
                    refreshDisplay();
                    JOptionPane.showMessageDialog(dialog, "Vehicle exited successfully!\nHours Parked: " + hoursParked + " hour(s)\nAmount Paid: $" + String.format("%.2f", totalFee), "Success", JOptionPane.INFORMATION_MESSAGE);
                    dialog.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
            cancelButton.addActionListener(e -> dialog.dispose());
            btnPanel.add(exitButton);
            btnPanel.add(Box.createHorizontalStrut(10));
            btnPanel.add(cancelButton);
            panel.add(btnPanel);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(dialog, "Error calculating fee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void refreshDisplay() {
        ParkingSlot[] allSlots = plaza.getAllSlots();
        for (int i = 0; i < allSlots.length && i < slotButtons.length; i++) {
            ParkingSlot slot = allSlots[i];
            if (slot != null && slotButtons[i] != null) {
                updateSlotButton(slotButtons[i], slot);
            }
        }
        updateStatistics();
    }
    
    private void updateStatistics() {
        totalSlotsLabel.setText("Total Slots: " + plaza.getTotalSlots());
        occupiedLabel.setText("Occupied: " + plaza.getOccupiedSlotCount());
        availableLabel.setText("Available: " + plaza.getAvailableSlotCount());
        revenueLabel.setText("Revenue: $" + String.format("%.2f", plaza.getTotalRevenue()));
    }
}
