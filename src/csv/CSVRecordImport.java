package csv;

import record.Customer;
import record.Town;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVRecordImport {
    public static List<Customer> loadRecordsFromCSV(String fileName) {
        List<Customer> recordList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
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
}
