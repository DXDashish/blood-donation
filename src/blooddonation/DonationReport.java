package blooddonation;

import java.sql.*;
import java.util.Scanner;

public class DonationReport {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter start date (YYYY-MM-DD): ");
        String start = sc.nextLine();
        System.out.print("Enter end date (YYYY-MM-DD): ");
        String end = sc.nextLine();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_donation", "root", "");
            String query = "SELECT d.date, d.quantity, donors.name FROM donations d " +
                           "JOIN donors ON d.donor_id = donors.id " +
                           "WHERE d.date BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, start);
            stmt.setString(2, end);
            ResultSet rs = stmt.executeQuery();

            System.out.println("=== Donation Report ===");
            while (rs.next()) {
                System.out.println("Date: " + rs.getString("date"));
                System.out.println("Donor: " + rs.getString("name"));
                System.out.println("Quantity: " + rs.getInt("quantity") + " ml");
                System.out.println("------------");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}