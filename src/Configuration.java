import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public enum Configuration {
    instance;

    public String userDirectory = System.getProperty("user.dir");
    public String fileSeparator = System.getProperty("file.separator");
    public String lineSeparator = System.lineSeparator();

    public String recordsFile = userDirectory + fileSeparator + "data" + fileSeparator + "customers.csv";
    public String logFile = userDirectory + fileSeparator + "log" + fileSeparator + "lambda_queries.log";

    public PrintWriter logger;
    public boolean enableLogging = true;

    public void initLogger() {
        try {
            logger = new PrintWriter(logFile);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public void log(String message) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String logMessage = dateFormat.format(new Date()) + message + lineSeparator;
        logger.write(logMessage);
        System.out.println(logMessage);
    }

    public void logQuery(String lambaExpression, String result) {
        log(": QUERY : " + lambaExpression);
        log(": RESULT: " + result);

    }
}
