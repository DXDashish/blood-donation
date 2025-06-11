package blooddonation;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BloodDonationGUI extends JFrame {
    private Connection conn;
    private JTabbedPane tabs;

    public BloodDonationGUI() {
        setTitle("Blood Donation Tracking System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        connectDatabase();
        login();
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_donation", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
        }
    }

    private void login() {
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        Object[] fields = {
            "Username:", userField,
            "Password:", passField
        };

        int option = JOptionPane.showConfirmDialog(null, fields, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    initializeTabs();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials.");
                    System.exit(0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Login failed: " + e.getMessage());
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }
    private void initializeTabs() {
        tabs = new JTabbedPane();

        tabs.add("Add Donor", getAddDonorPanel());
        tabs.add("View Donors", getViewDonorsPanel());
        tabs.add("Add Donation", getAddDonationPanel());
        tabs.add("View Donations", getViewDonationsPanel());
        tabs.add("View Stock", getViewStockPanel());
        tabs.add("Low Stock Alert", getLowStockAlertPanel());

        tabs.addChangeListener(e -> {
            int index = tabs.getSelectedIndex();
            String title = tabs.getTitleAt(index);
            switch (title) {
                case "View Donors" -> tabs.setComponentAt(index, getViewDonorsPanel());
                case "View Donations" -> tabs.setComponentAt(index, getViewDonationsPanel());
                case "View Stock" -> tabs.setComponentAt(index, getViewStockPanel());
                case "Low Stock Alert" -> tabs.setComponentAt(index, getLowStockAlertPanel());
            }
        });

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel getAddDonorPanel() {
        JPanel panel = new JPanel(null);
        JLabel nameL = new JLabel("Name:");
        JLabel bloodL = new JLabel("Blood Group:");
        JLabel ageL = new JLabel("Age:");
        JLabel contactL = new JLabel("Contact:");

        JTextField nameF = new JTextField();
        JComboBox<String> bloodF = new JComboBox<>(new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        JTextField ageF = new JTextField();
        JTextField contactF = new JTextField();

        JButton addBtn = new JButton("Add Donor");

        nameL.setBounds(50, 30, 100, 25); nameF.setBounds(160, 30, 200, 25);
        bloodL.setBounds(50, 70, 100, 25); bloodF.setBounds(160, 70, 200, 25);
        ageL.setBounds(50, 110, 100, 25); ageF.setBounds(160, 110, 200, 25);
        contactL.setBounds(50, 150, 100, 25); contactF.setBounds(160, 150, 200, 25);
        addBtn.setBounds(160, 200, 150, 30);

        panel.add(nameL); panel.add(nameF);
        panel.add(bloodL); panel.add(bloodF);
        panel.add(ageL); panel.add(ageF);
        panel.add(contactL); panel.add(contactF);
        panel.add(addBtn);

        addBtn.addActionListener(e -> {
            if (nameF.getText().isEmpty() || ageF.getText().isEmpty() || contactF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Please fill all fields.");
                return;
            }
            try {
                PreparedStatement stmt = conn.prepareStatement("INSERT INTO donors (name, blood_group, age, contact) VALUES (?, ?, ?, ?)");
                stmt.setString(1, nameF.getText());
                stmt.setString(2, (String) bloodF.getSelectedItem());
                stmt.setInt(3, Integer.parseInt(ageF.getText()));
                stmt.setString(4, contactF.getText());
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(panel, "Donor added successfully!");
                nameF.setText(""); ageF.setText(""); contactF.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel getViewDonorsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"ID", "Name", "Blood Group", "Age", "Contact"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM donors");
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("id"), rs.getString("name"), rs.getString("blood_group"),
                    rs.getInt("age"), rs.getString("contact")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }

        return panel;
    }

    private JPanel getAddDonationPanel() {
        JPanel panel = new JPanel(null);
        JLabel idL = new JLabel("Donor ID:");
        JLabel dateL = new JLabel("Date (YYYY-MM-DD):");
        JLabel qtyL = new JLabel("Quantity (ml):");

        JTextField idF = new JTextField();
        JTextField dateF = new JTextField();
        JTextField qtyF = new JTextField();

        JButton addBtn = new JButton("Add Donation");

        idL.setBounds(50, 30, 150, 25); idF.setBounds(200, 30, 200, 25);
        dateL.setBounds(50, 70, 150, 25); dateF.setBounds(200, 70, 200, 25);
        qtyL.setBounds(50, 110, 150, 25); qtyF.setBounds(200, 110, 200, 25);
        addBtn.setBounds(200, 160, 150, 30);

        panel.add(idL); panel.add(idF);
        panel.add(dateL); panel.add(dateF);
        panel.add(qtyL); panel.add(qtyF);
        panel.add(addBtn);

        addBtn.addActionListener(e -> {
            try {
                int donorId = Integer.parseInt(idF.getText());
                String date = dateF.getText();
                int qty = Integer.parseInt(qtyF.getText());

                PreparedStatement stmt = conn.prepareStatement("INSERT INTO donations (donor_id, date, quantity) VALUES (?, ?, ?)");
                stmt.setInt(1, donorId);
                stmt.setString(2, date);
                stmt.setInt(3, qty);
                stmt.executeUpdate();

                String bloodGroup = getDonorBloodGroup(donorId);
                if (bloodGroup != null) {
                    PreparedStatement update = conn.prepareStatement("UPDATE blood_stock SET quantity = quantity + ? WHERE blood_group = ?");
                    update.setInt(1, qty);
                    update.setString(2, bloodGroup);
                    update.executeUpdate();
                }

                JOptionPane.showMessageDialog(panel, "Donation added.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private String getDonorBloodGroup(int donorId) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT blood_group FROM donors WHERE id = ?");
        stmt.setInt(1, donorId);
        ResultSet rs = stmt.executeQuery();
        return rs.next() ? rs.getString("blood_group") : null;
    }

    private JPanel getViewDonationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"Donation ID", "Donor ID", "Date", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table));

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM donations");
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("id"), rs.getInt("donor_id"),
                    rs.getString("date"), rs.getInt("quantity")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

        return panel;
    }

    private JPanel getViewStockPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] cols = {"Blood Group", "Quantity"};
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        JTable table = new JTable(model);
        panel.add(new JScrollPane(table));

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM blood_stock");
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getString("blood_group"), rs.getInt("quantity")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }

        return panel;
    }

    private JPanel getLowStockAlertPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea area = new JTextArea();
        area.setEditable(false);
        panel.add(new JScrollPane(area));

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM blood_stock WHERE quantity < 500");

            boolean hasLow = false;
            while (rs.next()) {
                hasLow = true;
                area.append("\u26A0\uFE0F Low stock: " + rs.getString("blood_group") +
                        " (" + rs.getInt("quantity") + " ml)\n");
            }

            if (!hasLow) area.setText("\u2705 All blood stock levels are sufficient.");
        } catch (Exception e) {
            area.setText("Error: " + e.getMessage());
        }

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BloodDonationGUI().setVisible(true));
    }
}
