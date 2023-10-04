package log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter{

    private final LogEntries logEntries;

    public LogWriter(LogEntries logEntries) {
        this.logEntries = logEntries;
    }

    public synchronized void write(ActionDL actionDL) {
        String logFile = "log.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));) {
            writer.write(logEntries.writeActionDL(actionDL));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
