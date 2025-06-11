package blooddonation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DonationManager {
    public static void addDonation(int donorId, String date, int quantity) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            String query = "INSERT INTO donations (donor_id, date, quantity) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, donorId);
            stmt.setString(2, date);
            stmt.setInt(3, quantity);

            stmt.executeUpdate();
            System.out.println("✅ Donation record added.");

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        addDonation(1, "2025-05-29", 450); // Use real donor_id from your database
    }
}
