import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Application implements IQuery {
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

    // count
    public void executeSQL01(List<Customer> records) {

        System.out.println("Anzahl der List ist:");
        System.out.println(records.stream().count());
        System.out.println(records.size());
    }

    // count, where
    public void executeSQL02(List<Customer> customers) {
    }

    // count, where, in
    public void executeSQL03(List<Customer> customers) {
    }

    // count, where, not in
    public void executeSQL04(List<Customer> customers) {
    }

    // id, where, in, order by desc limit
    public void executeSQL05(List<Customer> customers) {
    }

    // id, where, in, order by desc, order by asc
    public void executeSQL06(List<Customer> customers) {
    }

    // count, group by
    public void executeSQL07(List<Customer> customers) {
    }

    // count, where, group by
    public void executeSQL08(List<Customer> customers) {
    }

    // count, where, in, group by
    public void executeSQL09(List<Customer> customers) {
    }

    // count, where, not in, group by
    public void executeSQL10(List<Customer> customers) {
    }

    // sum, where, not in, in, group by
    public void executeSQL11(List<Customer> customers) {
    }

    // avg, where, in, in, group by
    public void executeSQL12(List<Customer> customers) {
    }

    public void execute() {
        records = loadRecords();
        executeSQL01(new ArrayList<>(records));
        executeSQL02(new ArrayList<>(records));
        executeSQL03(new ArrayList<>(records));
        executeSQL04(new ArrayList<>(records));
        executeSQL05(new ArrayList<>(records));
        executeSQL06(new ArrayList<>(records));
        executeSQL07(new ArrayList<>(records));
        executeSQL08(new ArrayList<>(records));
        executeSQL09(new ArrayList<>(records));
        executeSQL10(new ArrayList<>(records));
        executeSQL11(new ArrayList<>(records));
        executeSQL12(new ArrayList<>(records));
    }

    public static void main(String... args) {
        Application app = new Application();
        app.execute();
    }
}

