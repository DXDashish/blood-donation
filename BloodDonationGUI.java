package bd;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JComboBox<String> roleComboBox;

    public LoginFrame() {
        setTitle("Blood Donation System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);

        // Role selector
        JLabel roleLabel = new JLabel("Select Role:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(roleLabel, gbc);

        roleComboBox = new JComboBox<>(new String[]{"Admin", "User", "Donor"});
        gbc.gridx = 1;
        add(roleComboBox, gbc);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0; gbc.gridy = 2;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        String role = (String) roleComboBox.getSelectedItem();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if(role.equals("Admin")) {
            if(username.equals("admin") && password.equals("admin123")) {
                JOptionPane.showMessageDialog(this, "Admin Login Successful");
                new AdminDashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Admin credentials!");
            }
        } else if(role.equals("User")) {
            if(!username.isEmpty() && !password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "User Login Successful");
                new UserDashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid User credentials!");
            }
        } else if(role.equals("Donor")) {
            if(!username.isEmpty() && !password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Donor Login Successful");
                new DonorDashboard();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Donor credentials!");
            }
        }
    }
}

class AdminDashboard extends JFrame {
    private static final long serialVersionUID = 5221225283713820311L;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 20, 20));

        JButton manageDonorsBtn = new JButton("Manage Donor Records");
        JButton manageUsersBtn = new JButton("Manage User Profiles");
        JButton viewRequestsBtn = new JButton("View Blood Requests");
        JButton viewEventsBtn = new JButton("View Event Records");
        JButton viewFeedbackBtn = new JButton("View Feedback");
        JButton logoutBtn = new JButton("Logout");

        buttonPanel.add(manageDonorsBtn);
        buttonPanel.add(manageUsersBtn);
        buttonPanel.add(viewRequestsBtn);
        buttonPanel.add(viewEventsBtn);
        buttonPanel.add(viewFeedbackBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);

        manageDonorsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manage Donor Records clicked"));
        manageUsersBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Manage User Profiles clicked"));
        viewRequestsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "View Blood Requests clicked"));
        viewEventsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "View Event Records clicked"));
        viewFeedbackBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "View Feedback clicked"));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}

class UserDashboard extends JFrame {
    private JTextField nameField, addressField, numberField, uidField;
    private JButton registerEventBtn, updateEventBtn, feedbackBtn, logoutBtn;

    public UserDashboard() {
        setTitle("User Dashboard");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Welcome, User!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // User info panel
        JPanel userInfoPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        userInfoPanel.setBorder(BorderFactory.createTitledBorder("User Information"));

        userInfoPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        userInfoPanel.add(nameField);

        userInfoPanel.add(new JLabel("Address:"));
        addressField = new JTextField();
        userInfoPanel.add(addressField);

        userInfoPanel.add(new JLabel("Number:"));
        numberField = new JTextField();
        userInfoPanel.add(numberField);

        userInfoPanel.add(new JLabel("UID:"));
        uidField = new JTextField();
        userInfoPanel.add(uidField);

        add(userInfoPanel, BorderLayout.CENTER);

        // Buttons panel for event interactions
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        registerEventBtn = new JButton("Register Event");
        updateEventBtn = new JButton("Update Event");
        feedbackBtn = new JButton("Provide Feedback");
        logoutBtn = new JButton("Logout");

        buttonPanel.add(registerEventBtn);
        buttonPanel.add(updateEventBtn);
        buttonPanel.add(feedbackBtn);
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        registerEventBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Register Event clicked"));
        updateEventBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Update Event clicked"));
        feedbackBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Provide Feedback clicked"));

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}


class DonorDashboard extends JFrame {
    private JTextField donorIdField, nameField, bloodGroupField, lastDonationDateField;
    private JCheckBox availabilityCheckBox;
    private JButton updateAvailabilityBtn, logoutBtn;

    public DonorDashboard() {
        setTitle("Donor Dashboard");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel titleLabel = new JLabel("Welcome, Donor!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Donor info panel
        JPanel donorInfoPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        donorInfoPanel.setBorder(BorderFactory.createTitledBorder("Donor Information"));

        donorInfoPanel.add(new JLabel("Donor ID:"));
        donorIdField = new JTextField();
        donorInfoPanel.add(donorIdField);

        donorInfoPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        donorInfoPanel.add(nameField);

        donorInfoPanel.add(new JLabel("Blood Group:"));
        bloodGroupField = new JTextField();
        donorInfoPanel.add(bloodGroupField);

        donorInfoPanel.add(new JLabel("Last Donation Date (YYYY-MM-DD):"));
        lastDonationDateField = new JTextField();
        donorInfoPanel.add(lastDonationDateField);

        add(donorInfoPanel, BorderLayout.CENTER);

        // Availability panel and buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        availabilityCheckBox = new JCheckBox("Available for Donation");
        bottomPanel.add(availabilityCheckBox);

        updateAvailabilityBtn = new JButton("Update Availability");
        bottomPanel.add(updateAvailabilityBtn);

        logoutBtn = new JButton("Logout");
        bottomPanel.add(logoutBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        // Action listeners
        updateAvailabilityBtn.addActionListener(e -> {
            boolean available = availabilityCheckBox.isSelected();
            JOptionPane.showMessageDialog(this, "Availability updated to: " + (available ? "Available" : "Not Available"));
            // Here you could add actual update logic (e.g., database)
        });

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame();
        });

        setVisible(true);
    }
}


