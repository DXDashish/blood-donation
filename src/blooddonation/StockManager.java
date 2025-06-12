
package blooddonation;

import java.sql.*;

public class StockManager {

    public static void updateStock(String bloodGroup, int quantity) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            // Check if blood group exists
            String select = "SELECT quantity FROM blood_stock WHERE blood_group = ?";
            PreparedStatement selectStmt = conn.prepareStatement(select);
            selectStmt.setString(1, bloodGroup);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int currentQty = rs.getInt("quantity");
                int newQty = currentQty + quantity;

                String update = "UPDATE blood_stock SET quantity = ? WHERE blood_group = ?";
                PreparedStatement updateStmt = conn.prepareStatement(update);
                updateStmt.setInt(1, newQty);
                updateStmt.setString(2, bloodGroup);
                updateStmt.executeUpdate();
                System.out.println("✅ Stock updated.");
            } else {
                // If no record exists, insert new
                String insert = "INSERT INTO blood_stock (blood_group, quantity) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insert);
                insertStmt.setString(1, bloodGroup);
                insertStmt.setInt(2, quantity);
                insertStmt.executeUpdate();
                System.out.println("✅ Stock record created.");
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        updateStock("O+", 450); // Example
    }
}
