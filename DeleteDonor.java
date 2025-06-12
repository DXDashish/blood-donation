package blooddonation;

import java.sql.*;
import java.util.Scanner;

public class DeleteDonor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Donor ID to delete: ");
        int donorId = scanner.nextInt();

        System.out.print("Are you sure you want to delete donor ID " + donorId + "? (yes/no): ");
        String confirm = scanner.next();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("❌ Deletion cancelled.");
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_donation", "root", "");
            String query = "DELETE FROM donors WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, donorId);
            int result = stmt.executeUpdate();

            if (result > 0) {
                System.out.println("✅ Donor deleted successfully.");
            } else {
                System.out.println("❌ Donor ID not found.");
            }
/// for test only 
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}