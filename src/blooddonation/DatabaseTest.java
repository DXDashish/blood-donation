package blooddonation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to MySQL (XAMPP)
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            // If connection is successful
            System.out.println("✅ Connected to MySQL database successfully!");
            conn.close(); // Close connection after test

        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Failed to connect to the database.");
            e.printStackTrace();
        }
    }
}