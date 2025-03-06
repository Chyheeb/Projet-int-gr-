package utils;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoggerUtil {

    private static final String LOG_FILE = "C:/Users/asus tuf/ProjetWeb3A/citicore1/src/main/java/utils/log.txt";

    public static void logAction(String action, String details) {

        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pw.println("[" + timestamp + "] " + action + " - " + details);

        } catch (IOException e) {
            System.out.println("Erreur lors de l'écriture du log: " + e.getMessage());
        }
    }
}