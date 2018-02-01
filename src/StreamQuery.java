import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamQuery implements IQuery {

    /*
     * 1. SQL Query:
     *     SELECT COUNT(*) FROM data
     */

    // count
    public long executeSQL01(List<Customer> customers) {
        return customers.stream().count();
    }

    /*
     * 2. SQL Query:
     *     SELECT COUNT(*) FROM data
     *         WHERE region = 'A'
     *         AND type = 'S'
     */

    // count, where
    public long executeSQL02(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> customer.getTown().getRegion().equals("A")
                        && customer.getType().equals("S"))
                .count();
    }

    /*
     * 3. SQL Query:
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
                        && Arrays.asList("S", "L").contains(customer.getType())
                        && customer.getEnergyConsumption0To6() >= 25
                        && customer.getEnergyConsumption0To6() <= 50)
                .count();
    }

    /*
     * 4. SQL Query:
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
                .filter(customer -> !Arrays.asList("L", "M").contains(customer.getType())
                        && customer.getTown().getRegion().equals("B")
                        && customer.getBonusLevel() >= 2
                        && customer.hasSmartTechnology()
                        && customer.getEnergyConsumption12To18() <= 25)
                .count();
    }

    /*
     * 5. SQL Query:
     *     SELECT id FROM data
     *         WHERE region = 'A'
     *         AND type IN ('S', 'L')
     *         AND energyConsumption0To6 >= 25
     *         AND energyConsumption0To6 <= 50
     */

    // id, where, in, order by desc limit
    public List<Integer> executeSQL05(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> customer.getTown().getRegion().equals("A")
                        && Arrays.asList("S", "L").contains(customer.getType())
                        && customer.getEnergyConsumption0To6() >= 25
                        && customer.getEnergyConsumption0To6() <= 50)
                .sorted((x, y) -> y.getEnergyConsumption0To6() - x.getEnergyConsumption0To6())
                .limit(3)
                .map(Customer::getId)
                .collect(Collectors.toList());

    }

    /*
     * 6. SQL Query:
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
                        && Arrays.asList("K", "L").contains(customer.getType())
                        && customer.getBonusLevel() <= 2
                        && customer.hasSmartTechnology()
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
     * 7. SQL Query:
     *     SELECT hasSmartTechnology, COUNT(*) FROM data
     *         GROUP BY hasSmartTechnology
     */

    // count, group by
    public Map<Boolean, Long> executeSQL07(List<Customer> customers) {
        return customers
                .stream()
                .collect(Collectors.partitioningBy(Customer::hasSmartTechnology,
                        Collectors.counting()));
    }

    /*
     * 8. SQL Query:
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
     * 9. SQL Query:
     *     SELECT bonusLevel, COUNT(*) FROM data
     *         WHERE TYPE IN ('L', 'M')
     *         GROUP BY bonusLevel
     */

    // count, where, in, group by
    public Map<Integer, Long> executeSQL09(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> Arrays.asList("L", "M").contains(customer.getType()))
                .collect(Collectors.groupingBy(Customer::getBonusLevel,
                        Collectors.counting()));
    }

    /*
     * 10. SQL Query:
     *     SELECT region, COUNT(*) FROM data
     *         WHERE region NOT IN ('A', 'B')
     *         AND hasSmartTechnology = 'true'
     *         GROUP BY region
     */

    // count, where, not in, group by
    public Map<String, Long> executeSQL10(List<Customer> customers) {
        return customers
                .stream()
                .filter(customer -> !Arrays.asList("A", "B").contains(customer.getTown().getRegion())
                        && customer.hasSmartTechnology())
                .collect(Collectors.groupingBy(customer -> customer.getTown().getRegion(),
                        Collectors.counting()));
    }

    /*
     * 11. SQL Query:
     *     SELECT hasSmartTechnology, SUM(energyConsumption6To12) FROM data
     *         WHERE region NOT IN ('B', 'C')
     *         AND bonusLevel IN (1, 3)
     *         AND type = 'M'
     *         GROUP BY hasSmartTechnology
     */

    // sum, where, not in, in, group by
    public Map<Boolean, Long> executeSQL11(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> !Arrays.asList("B", "C").contains(customer.getTown().getRegion())
                        && Arrays.asList(1, 3).contains(customer.getBonusLevel())
                        && customer.getType().equals("M"))
                .collect(Collectors.partitioningBy(Customer::hasSmartTechnology,
                        Collectors.summingLong(Customer::getEnergyConsumption6To12)));
    }

    /*
     * 12. SQL Query:
     *     SELECT region, AVG(energyConsumption6To12) FROM data
     *         WHERE region IN ('A', 'C')
     *         AND type IN ('L', 'M')
     *         AND hasSmartTechnology = 'false'
     *         GROUP BY region
     */

    // avg, where, in, in, group by
    public Map<String, Long> executeSQL12(List<Customer> customers) {
        return customers.stream()
                .filter(customer -> Arrays.asList("A", "C").contains(customer.getTown().getRegion())
                        && Arrays.asList("L", "M").contains(customer.getType())
                        && !customer.hasSmartTechnology())
                .collect(Collectors.groupingBy(customer -> customer.getTown().getRegion(),
                        Collectors.averagingInt(Customer::getEnergyConsumption6To12)))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().longValue()));
    }
}
