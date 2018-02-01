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

    public void initLogger() {
        try {
            logger = new PrintWriter(logFile);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public void logQuery(String lambaExpression, String result) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();
        String dateString = dateFormat.format(currentDate);
        logger.write(dateString + ": QUERY : " + lambaExpression + lineSeparator);
        logger.write(dateString + ": RESULT: " + result + lineSeparator);

    }
}
