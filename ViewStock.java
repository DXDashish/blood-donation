package blooddonation;

import java.sql.*;

public class ViewStock {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            String query = "SELECT * FROM blood_stock";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            System.out.println("=== Blood Stock ===");
            while (rs.next()) {
                System.out.println("Blood Group: " + rs.getString("blood_group"));
                System.out.println("Quantity (ml): " + rs.getInt("quantity"));
                System.out.println("---------------");
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
