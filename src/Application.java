import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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

    /*
     * SQL Query:
     *     SELECT COUNT(*) FROM data
     */

    // count
    public long executeSQL01(List<Customer> customers) {
        return customers.stream().count();
    }

    /*
     * SQL Query:
     *     SELECT COUNT(*) FROM data
     *         WHERE region = 'A'
     *         AND type = 'S'
     */

    // count, where
    public long executeSQL02(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> customer.getTown().getRegion().equals("A"))
                .filter(customer -> customer.getType().equals("S"))
                .count();
    }

    /*
     * SQL Query:
     *     SELECT COUNT(*) FROM data
     *         WHERE region = 'A'
     *         AND type IN ('S', 'L')
     *         AND energyConsumption0To6 >= 25
     *         AND energyConsumption0To6 <= 50
     */

    // count, where, in
    public long executeSQL03(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> customer.getTown().getRegion().equals("A")
                        && customer.getType().matches("[SL]")
                        && customer.getEnergyConsumption0To6() >= 25
                        && customer.getEnergyConsumption0To6() <= 50)
                .count();
    }

    /*
     * SQL Query:
     *     SELECT COUNT(*) FROM data
     *         WHERE type NOT IN ('L', 'M')
     *         AND region = 'B'
     *         AND bonusLevel >= 2
     *         AND hasSmartTechnology = 'true'
     *         AND energyConsumption12To18 <= 25
     */

    // count, where, not in
    public long executeSQL04(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> customer.getType().matches("[^LM]"))
                .filter(customer -> customer.getTown().getRegion().equals("B"))
                .filter(customer -> customer.getBonusLevel() >= 2)
                .filter(customer -> customer.isHasSmartTechnology())
                .filter(customer -> customer.getEnergyConsumption12To18() <= 25)
                .count();
    }

    /*
     * SQL Query:
     *     SELECT id FROM data
     *         WHERE region = 'A'
     *         AND type IN ('S', 'L')
     *         AND energyConsumption0To6 >= 25
     *         AND energyConsumption0To6 <= 50
     */

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

    /*
     * SQL Query:
     *     SELECT id FROM data
     *         WHERE region = 'C'
     *         AND type IN ('K', 'L')
     *         AND bonusLevel <= 2
     *         AND hasSmartTechnology = 'true'
     *         AND energyConsumption0To6 <= 5
     *         AND energyConsumption6To12 >= 10
     *         AND energyConsumption6To12 <= 15
     *         ORDER BY bonusLevel DESC, energyConsumption6To12
     */

    // id, where, in, order by desc, order by asc
    public List<Integer> executeSQL06(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> customer.getTown().getRegion().equals("C")
                        && customer.getType().matches("[KL]")
                        && customer.getBonusLevel() <= 2
                        && customer.isHasSmartTechnology()
                        && customer.getEnergyConsumption0To6() <= 5
                        && customer.getEnergyConsumption6To12() >= 10
                        && customer.getEnergyConsumption6To12() <= 15)
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

    /*
     * SQL Query:
     *     SELECT hasSmartTechnology, COUNT(*) FROM data
     *         GROUP BY hasSmartTechnology
     */

    // count, group by
    public Map<Boolean, Long> executeSQL07(List<Customer> customers) {
        return customers
                .stream()
                .collect(Collectors.groupingBy(Customer::isHasSmartTechnology,
                        Collectors.counting()));
    }

    /*
     * SQL Query:
     *     SELECT region, COUNT(*) FROM data
     *         WHERE energyConsumption0To6 <= 50
     *         GROUP BY region
     */

    // count, where, group by
    public Map<String, Long> executeSQL08(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> customer.getEnergyConsumption0To6() <= 50)
                .collect(Collectors.groupingBy(customer -> customer.getTown().getRegion(),
                        Collectors.counting()));
    }

    /*
     * SQL Query:
     *     SELECT bonusLevel, COUNT(*) FROM data
     *         WHERE TYPE IN ('L', 'M')
     *         GROUP BY bonusLevel
     */

    // count, where, in, group by
    public Map<Integer, Long> executeSQL09(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> customer.getType().matches("[LM]"))
                .collect(Collectors.groupingBy(Customer::getBonusLevel,
                        Collectors.counting()));
    }

    /*
     * SQL Query:
     *     SELECT region, COUNT(*) FROM data
     *         WHERE region NOT IN ('A', 'B')
     *         AND hasSmartTechnology = 'true'
     */

    // count, where, not in, group by
    public Map<String, Long> executeSQL10(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> customer.getType().matches("[^AB]")
                        && customer.isHasSmartTechnology())
                .collect(Collectors.groupingBy(
                        customer -> customer.getTown().getRegion(), Collectors.counting())
                );
    }

    /*
     * SQL Query:
     *     SELECT hasSmartTechnology, SUM(energyConsumption6To12) FROM data
     *         WHERE region NOT IN ('B', 'C')
     *         AND bonusLevel IN (1, 3)
     *         AND type = 'M'
     *         GROUP BY hasSmartTechnology
     */

    // sum, where, not in, in, group by
    public Map<Boolean, Integer> executeSQL11(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> !Arrays.asList("B", "C").contains(customer.getTown().getRegion())
                        && Arrays.asList(1, 3).contains(customer.getBonusLevel())
                        && customer.getType().equals("M"))
                .collect(Collectors.groupingBy(Customer::isHasSmartTechnology,
                        Collectors.summingInt(Customer::getEnergyConsumption6To12)));
    }

    /*
     * SQL Query:
     *     SELECT region, AVG(energyConsumption6To12) FROM data
     *         WHERE region IN ('A', 'C')
     *         AND type IN ('L', 'M')
     *         AND hasSmartTechnology = 'false'
     *         GROUP BY region
     */

    // avg, where, in, in, group by
    public Map<String, Integer> executeSQL12(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> Arrays.asList("A", "C").contains(customer.getTown().getRegion())
                        && Arrays.asList("L", "M").contains(customer.getType())
                        && !customer.isHasSmartTechnology())
                .collect(Collectors.groupingBy(customer -> customer.getTown().getRegion(),
                        Collectors.averagingInt(Customer::getEnergyConsumption6To12)))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().intValue()));
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

