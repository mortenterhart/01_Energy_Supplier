package main;

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
        String logPrefix = "[[INFO] " + dateFormat.format(new Date()) + "]: ";
        String logMessage = logPrefix + message.replaceAll("\\n", "\n" + logPrefix) + lineSeparator;
        logger.write(logMessage);
        logger.flush();
        System.out.print(logMessage);
    }

    public void logQuery(String sqlStatement, String lambdaExpression, Object result) {
        log("SQL statement: " + sqlStatement);
        log("LambdaStream: " + lambdaExpression);
        logNewLine();
        log("Result set:   " + result.toString());
    }

    public void logNewLine() {
        log("");
    }

    public void closeLogger() {
        logger.flush();
        logger.close();
    }
}
