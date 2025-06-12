package blooddonation;

import java.sql.*;
import java.util.Scanner;

public class FilterDonors{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter minimum age: ");
        int min = sc.nextInt();
        System.out.print("Enter maximum age: ");
        int max = sc.nextInt();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blood_donation", "root", "");
            String query = "SELECT * FROM donors WHERE age BETWEEN ? AND ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, min);
            stmt.setInt(2, max);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Blood Group: " + rs.getString("blood_group"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Contact: " + rs.getString("contact"));
                System.out.println("------------");
            }

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}