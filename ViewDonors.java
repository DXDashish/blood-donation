package blooddonation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewDonors {
    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/blood_donation", "root", ""
            );

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM donors");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Blood Group: " + rs.getString("blood_group"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Contact: " + rs.getString("contact"));
                System.out.println("-----------");
            }

            conn.close();

        } catch (Exception e) {
            System.out.println("❌ Error fetching donors: " + e.getMessage());
        }
    }
}