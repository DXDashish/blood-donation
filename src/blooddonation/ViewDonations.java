package blooddonation;

import java.sql.*;

public class ViewDonations {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            String query = "SELECT d.id, d.date, d.quantity, donors.name FROM donations d JOIN donors ON d.donor_id = donors.id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                System.out.println("Donation ID: " + rs.getInt("id"));
                System.out.println("Donor Name: " + rs.getString("name"));
                System.out.println("Date: " + rs.getString("date"));
                System.out.println("Quantity (ml): " + rs.getInt("quantity"));
                System.out.println("---------------");
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
