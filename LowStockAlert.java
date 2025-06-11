package blooddonation;

import java.sql.*;

public class LowStockAlert {
    public static void main(String[] args) {
        int threshold = 500;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_donation", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM blood_stock");

            boolean found = false;
            while (rs.next()) {
                String group = rs.getString("blood_group");
                int qty = rs.getInt("quantity");
                if (qty < threshold) {
                    System.out.println("⚠️ Blood group " + group + " is low (" + qty + " ml left)");
                    found = true;
                }
            }

            if (!found) {
                System.out.println("✅ All blood stocks are sufficient.");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}