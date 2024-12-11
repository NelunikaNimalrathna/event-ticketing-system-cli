import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// logger class to log the system events
public class Logger {
    private static final String LOG_FILE = "system.log";

    public static synchronized void log(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
}

