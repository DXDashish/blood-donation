package blooddonation;

import java.sql.*;
import java.util.Scanner;

public class searchDonors {
	  public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter blood group to search: ");
        String bloodGroup = scanner.nextLine();

        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            String query = "SELECT * FROM donors WHERE blood_group = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, bloodGroup);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Contact: " + rs.getString("contact"));
                System.out.println("-----------");
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        scanner.close();
    }
}
