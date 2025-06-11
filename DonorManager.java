package blooddonation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DonorManager {

    public static void addDonor(String name, String bloodGroup, int age, String contact) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            // Insert query
            String query = "INSERT INTO donors (name, blood_group, age, contact) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, bloodGroup);
            stmt.setInt(3, age);
            stmt.setString(4, contact);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Donor added successfully!");
            }

            conn.close();

        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Failed to add donor.");
            e.printStackTrace();
        }
    }

    // Main method to test
    public static void main(String[] args) {
        addDonor("Shyam Basnet", "O+", 28, "9841234567");
    }
}