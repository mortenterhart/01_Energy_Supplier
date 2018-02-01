import java.io.*;
import java.util.*;

public class Application {
    private List<Customer> records = new ArrayList<>();

    public List<Customer> loadRecords() {
        List<Customer> recordList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(Configuration.instance.recordsFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] properties = line.split(";");

                int customerId = Integer.parseInt(properties[0]);
                int townId = Integer.parseInt(properties[1]);
                String type = properties[2];
                int bonusLevel = Integer.parseInt(properties[3]);
                boolean smartTechnology = Boolean.parseBoolean(properties[4]);
                String region = properties[5];
                int energyConsumption0To6 = Integer.parseInt(properties[6]);
                int energyConsumption6To12 = Integer.parseInt(properties[7]);
                int energyConsumption12To18 = Integer.parseInt(properties[8]);
                int energyConsumption18To24 = Integer.parseInt(properties[9]);

                Town town = new Town(townId, region);
                Customer customer = new Customer(customerId, town, type, bonusLevel, smartTechnology,
                        energyConsumption0To6, energyConsumption6To12, energyConsumption12To18,
                        energyConsumption18To24);

                recordList.add(customer);
            }
        } catch (IOException | NumberFormatException exc) {
            System.err.println(exc.getMessage());
        }
        return recordList;
    }

    public void execute() {
        records = loadRecords();
        IQuery streamQuery = new StreamQuery();
        if (Configuration.instance.enableLogging) {
            Configuration.instance.log("### Started Execution of Lambda Streams");
            streamQuery.executeSQL01(new ArrayList<>(records));
            streamQuery.executeSQL02(new ArrayList<>(records));
            streamQuery.executeSQL03(new ArrayList<>(records));
            streamQuery.executeSQL04(new ArrayList<>(records));
            streamQuery.executeSQL05(new ArrayList<>(records));
            streamQuery.executeSQL06(new ArrayList<>(records));
            streamQuery.executeSQL07(new ArrayList<>(records));
            streamQuery.executeSQL08(new ArrayList<>(records));
            streamQuery.executeSQL09(new ArrayList<>(records));
            streamQuery.executeSQL10(new ArrayList<>(records));
            streamQuery.executeSQL11(new ArrayList<>(records));
            streamQuery.executeSQL12(new ArrayList<>(records));
        } else {
            streamQuery.executeSQL01(new ArrayList<>(records));
            streamQuery.executeSQL02(new ArrayList<>(records));
            streamQuery.executeSQL03(new ArrayList<>(records));
            streamQuery.executeSQL04(new ArrayList<>(records));
            streamQuery.executeSQL05(new ArrayList<>(records));
            streamQuery.executeSQL06(new ArrayList<>(records));
            streamQuery.executeSQL07(new ArrayList<>(records));
            streamQuery.executeSQL08(new ArrayList<>(records));
            streamQuery.executeSQL09(new ArrayList<>(records));
            streamQuery.executeSQL10(new ArrayList<>(records));
            streamQuery.executeSQL11(new ArrayList<>(records));
            streamQuery.executeSQL12(new ArrayList<>(records));
        }

    }

    public static void main(String[] args) {
        Application app = new Application();
        app.execute();
    }
}

