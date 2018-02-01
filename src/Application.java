import java.io.*;

import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Application implements IStreamQuery {
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

    public void executeSQL01(List<Customer> customers) {
        System.out.println("Anzahl der List ist:");
        System.out.println(
                customers.
                        stream().
                        count()
        );
        //System.out.println(records.size());

    }

    // count, where
    public void executeSQL02(List<Customer> customers) {
        System.out.println(
                customers
                        .stream()
                        .filter(customer -> customer.getTown().getRegion().equals("A"))
                        .filter(customer -> customer.getType().equals("S"))
                        .count()
        );
    }

    // count, where, in
    public void executeSQL03(List<Customer> customers) {
        System.out.println(
                customers
                        .stream()
                        .filter(customer -> customer.getTown().getRegion().equals("A")
                                && customer.getType().matches("[SL]")
                                && customer.getEnergyConsumption0To6() >= 25
                                && customer.getEnergyConsumption0To6() <= 50)
                        .count()
        );
    }

    // count, where, not in
    public void executeSQL04(List<Customer> customers) {
        System.out.println(
                customers
                        .stream()
                        .filter(customer -> customer.getType().matches("[^LM]") )
                        .filter(customer -> customer.getTown().getRegion().equals("B") )
                        .filter(customer -> customer.getBonusLevel() >= 2)
                        .filter(customer -> customer.isHasSmartTechnology())
                        .filter(customer -> customer.getEnergyConsumption12To18() <= 25)
                        .count()
        );
    }

    // id, where, in, order by desc limit
    public List<Integer> executeSQL05(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> customer.getTown().getRegion().equals("A") &&
                        customer.getType().matches("[SL]") &&
                        customer.getEnergyConsumption0To6() >= 25 &&
                        customer.getEnergyConsumption0To6() <= 50)
                .sorted((x, y) -> y.getEnergyConsumption0To6() - x.getEnergyConsumption0To6())
                .limit(3)
                .map(Customer::getId)
                .collect(Collectors.toList());

    }

    // id, where, in, order by desc, order by asc
    public List<Integer> executeSQL06(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> customer.getTown().getRegion().equals("C") &&
                        customer.getType().matches("[KL]") &&
                        customer.getBonusLevel() <= 2 &&
                        customer.isHasSmartTechnology() &&
                        customer.getEnergyConsumption0To6() <= 5 &&
                        customer.getEnergyConsumption6To12() >= 10 &&
                        customer.getEnergyConsumption6To12() <= 15)
                .sorted((o1, o2) -> {
                    int bonusLevelOrder = o2.getBonusLevel() - o1.getBonusLevel();
                    if (bonusLevelOrder != 0) {
                        return bonusLevelOrder;
                    }
                    return o1.getEnergyConsumption6To12() - o2.getEnergyConsumption6To12();
                })
                .map(Customer::getId)
                .collect(Collectors.toList());
    }

    // count, group by
    public void executeSQL07(List<Customer> customers) {
        System.out.println(
                customers
                        .stream()
                        .collect(Collectors.groupingBy(
                                customer -> customer.isHasSmartTechnology(), Collectors.counting() )
                        )
        );
    }

    // count, where, group by
    public void executeSQL08(List<Customer> customers) {
        System.out.println(
                customers
                        .stream()
                        .filter(customer -> customer.getEnergyConsumption0To6() <= 50)
                        .collect(Collectors.groupingBy(
                                customer -> customer.getTown().getRegion(), Collectors.counting() )
                        )
        );
    }

    // count, where, in, group by
    public void executeSQL09(List<Customer> customers) {
        System.out.println(
                customers
                        .stream()
                        .filter(customer -> customer.getType().matches("[LM]") )
                        .collect(Collectors.groupingBy(
                                customer -> customer.getBonusLevel(), Collectors.counting())
                        )
        );
    }

    // count, where, not in, group by
    public void executeSQL10(List<Customer> customers) {
        System.out.println(
                customers
                        .stream()
                        .filter(customer -> customer.getType().matches("[^AB]")
                                && customer.isHasSmartTechnology())
                        .collect(Collectors.groupingBy(
                                customer -> customer.getTown().getRegion(), Collectors.counting())
                        )
        );
    }

    // sum, where, not in, in, group by
    public Map<Boolean, Integer> executeSQL11(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> Arrays.asList("B", "C").contains(customer.getTown().getRegion()) &&
                        Arrays.asList(1, 3).contains(customer.getBonusLevel()) &&

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

        executeSQL02(records);
    }

    public static void main(String... args) {
        Application app = new Application();
        app.execute();
    }
}

