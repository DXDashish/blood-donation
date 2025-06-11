package blooddonation;

import java.io.*;
import java.util.*;

public class BackupDatabase {
    public static void main(String[] args) {
        try {
            String backupFile = "backup.sql";
            ProcessBuilder pb = new ProcessBuilder(
                "cmd.exe", "/c", "mysqldump -u root blood_donation > " + backupFile
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            System.out.println("⏳ Creating backup...");
            process.waitFor();
            System.out.println("✅ Backup created as " + backupFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}