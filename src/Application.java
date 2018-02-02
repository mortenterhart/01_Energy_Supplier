import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Application {
    private List<Customer> records = new ArrayList<>();

    public void log(String message) {
        Configuration.instance.log(message);
    }

    public void logQuery(int queryId, String sqlStatement, String lambdaExpression, Object result) {
        Configuration.instance.log(String.format("--> Query %02d: executeSQL%02d(customers)", queryId, queryId));
        Configuration.instance.logQuery(sqlStatement, lambdaExpression, result);
    }

    public void execute() {
        records = CSVRecordImport.loadRecordsFromCSV(Configuration.instance.recordsFile);
        IQuery streamQuery = new StreamQuery();
        if (Configuration.instance.enableLogging) {
            Configuration.instance.initLogger();
            log("### Started Execution of Stream Queries ###");
            Configuration.instance.logNewLine();

            int query = 1;
            long result = 0;
            List<Integer> resultList;

            result = streamQuery.executeSQL01(new ArrayList<>(records));
            logQuery(query, "SELECT COUNT(*) FROM data",
                    "customers.stream().count()", result);
            Configuration.instance.logNewLine();
            query++;

            result = streamQuery.executeSQL02(new ArrayList<>(records));
            logQuery(query, "SELECT COUNT(*) FROM data WHERE region = 'A' AND type = 'S'",
                    "customers\n" +
                            "                .stream()\n" +
                            "                .filter(customer -> customer.getTown().getRegion().equals(\"A\")\n" +
                            "                        && customer.getType().equals(\"S\"))\n" +
                            "                .count()", result);
            Configuration.instance.logNewLine();
            query++;

            result = streamQuery.executeSQL03(new ArrayList<>(records));
            logQuery(query, "SELECT COUNT(*) FROM data\n" +
                    "    WHERE region = 'A'\n" +
                    "    AND type IN ('S', 'L')\n" +
                    "    AND energyConsumption0To6 >= 25\n" +
                    "    AND energyConsumption0To6 <= 50", "customers\n" +
                    "                .stream()\n" +
                    "                .filter(customer -> customer.getTown().getRegion().equals(\"A\")\n" +
                    "                        && customer.getType().matches(\"[SL]\")\n" +
                    "                        && customer.getEnergyConsumption0To6() >= 25\n" +
                    "                        && customer.getEnergyConsumption0To6() <= 50)\n" +
                    "                .count()", result);
            Configuration.instance.logNewLine();
            query++;

            result = streamQuery.executeSQL04(new ArrayList<>(records));
            logQuery(query, "SELECT COUNT(*) FROM data\n" +
                    "    WHERE type NOT IN ('L', 'M')\n" +
                    "    AND region = 'B'\n" +
                    "    AND bonusLevel >= 2\n" +
                    "    AND hasSmartTechnology = 'true'\n" +
                    "    AND energyConsumption12To18 <= 25", "customers\n" +
                    "                .stream()\n" +
                    "                .filter(customer -> customer.getType().matches(\"[^LM]\")\n" +
                    "                        && customer.getTown().getRegion().equals(\"B\")\n" +
                    "                        && customer.getBonusLevel() >= 2\n" +
                    "                        && customer.hasSmartTechnology()\n" +
                    "                        && customer.getEnergyConsumption12To18() <= 25)\n" +
                    "                .count()", result);
            Configuration.instance.logNewLine();
            query++;

            resultList = streamQuery.executeSQL05(new ArrayList<>(records));
            logQuery(query, "SELECT id FROM data\n" +
                    "    WHERE region = 'A'\n" +
                    "    AND type IN ('S', 'L')\n" +
                    "    AND energyConsumption0To6 >= 25\n" +
                    "    AND energyConsumption0To6 <= 50", "customers.stream()\n" +
                    "                .filter(customer -> customer.getTown().getRegion().equals(\"A\")\n" +
                    "                        && Arrays.asList(\"S\", \"L\").contains(customer.getType())\n" +
                    "                        && customer.getEnergyConsumption0To6() >= 25\n" +
                    "                        && customer.getEnergyConsumption0To6() <= 50)\n" +
                    "                .sorted((x, y) -> y.getEnergyConsumption0To6() - x.getEnergyConsumption0To6())\n" +
                    "                .limit(3)\n" +
                    "                .map(Customer::getId)\n" +
                    "                .collect(Collectors.toList())", resultList);
            Configuration.instance.logNewLine();
            query++;

            resultList = streamQuery.executeSQL06(new ArrayList<>(records));
            logQuery(query, "SELECT id FROM data\n" +
                    "    WHERE region = 'C'\n" +
                    "    AND type IN ('K', 'L')\n" +
                    "    AND bonusLevel <= 2\n" +
                    "    AND hasSmartTechnology = 'true'\n" +
                    "    AND energyConsumption0To6 <= 5\n" +
                    "    AND energyConsumption6To12 >= 10\n" +
                    "    AND energyConsumption6To12 <= 15\n" +
                    "    ORDER BY bonusLevel DESC, energyConsumption6To12", "customers.stream()\n" +
                    "                .filter(customer -> customer.getTown().getRegion().equals(\"C\")\n" +
                    "                        && Arrays.asList(\"K\", \"L\").contains(customer.getType())\n" +
                    "                        && customer.getBonusLevel() <= 2\n" +
                    "                        && customer.hasSmartTechnology()\n" +
                    "                        && customer.getEnergyConsumption0To6() <= 5\n" +
                    "                        && customer.getEnergyConsumption6To12() >= 10\n" +
                    "                        && customer.getEnergyConsumption6To12() <= 15)\n" +
                    "                .sorted((o1, o2) -> {\n" +
                    "                    int bonusLevelOrder = o2.getBonusLevel() - o1.getBonusLevel();\n" +
                    "                    if (bonusLevelOrder != 0) {\n" +
                    "                        return bonusLevelOrder;\n" +
                    "                    }\n" +
                    "                    return o1.getEnergyConsumption6To12() - o2.getEnergyConsumption6To12();\n" +
                    "                })\n" +
                    "                .map(Customer::getId)\n" +
                    "                .collect(Collectors.toList())", resultList);
            Configuration.instance.logNewLine();
            query++;

            Map<Boolean, Long> resultMap1 = streamQuery.executeSQL07(new ArrayList<>(records));
            logQuery(query, "SELECT hasSmartTechnology, COUNT(*) FROM data\n" +
                    "    GROUP BY hasSmartTechnology", "customers\n" +
                    "                .stream()\n" +
                    "                .collect(Collectors.partitioningBy(Customer::hasSmartTechnology,\n" +
                    "                        Collectors.counting()))", resultMap1);
            Configuration.instance.logNewLine();
            query++;

            Map<String, Long> resultMap2 = streamQuery.executeSQL08(new ArrayList<>(records));
            logQuery(query, "SELECT region, COUNT(*) FROM data\n" +
                    "    WHERE energyConsumption0To6 <= 50\n" +
                    "    GROUP BY region", "customers\n" +
                    "                .stream()\n" +
                    "                .filter(customer -> customer.getEnergyConsumption0To6() <= 50)\n" +
                    "                .collect(Collectors.groupingBy(customer -> customer.getTown().getRegion(),\n" +
                    "                        Collectors.counting()))", resultMap2);
            Configuration.instance.logNewLine();
            query++;

            Map<Integer, Long> resultMap3 = streamQuery.executeSQL09(new ArrayList<>(records));
            logQuery(query, "SELECT bonusLevel, COUNT(*) FROM data\n" +
                    "    WHERE TYPE IN ('L', 'M')\n" +
                    "    GROUP BY bonusLevel", "customers\n" +
                    "                .stream()\n" +
                    "                .filter(customer -> Arrays.asList(\"L\", \"M\").contains(customer.getType()))\n" +
                    "                .collect(Collectors.groupingBy(Customer::getBonusLevel,\n" +
                    "                        Collectors.counting()))", resultMap3);
            Configuration.instance.logNewLine();
            query++;

            Map<String, Long> resultMap4 = streamQuery.executeSQL10(new ArrayList<>(records));
            logQuery(query, "SELECT region, COUNT(*) FROM data\n" +
                    "    WHERE region NOT IN ('A', 'B')\n" +
                    "    AND hasSmartTechnology = 'true'\n" +
                    "    GROUP BY region", "customers\n" +
                    "                .stream()\n" +
                    "                .filter(customer -> !Arrays.asList(\"A\", \"B\").contains(customer.getTown().getRegion())\n" +
                    "                        && customer.hasSmartTechnology())\n" +
                    "                .collect(Collectors.groupingBy(customer -> customer.getTown().getRegion(),\n" +
                    "                        Collectors.counting()))", resultMap4);
            Configuration.instance.logNewLine();
            query++;

            Map<Boolean, Long> resultMap5 = streamQuery.executeSQL11(new ArrayList<>(records));
            logQuery(query, "SELECT hasSmartTechnology, SUM(energyConsumption6To12) FROM data\n" +
                    "    WHERE region NOT IN ('B', 'C')\n" +
                    "    AND bonusLevel IN (1, 3)\n" +
                    "    AND type = 'M'\n" +
                    "    GROUP BY hasSmartTechnology", "customers.stream()\n" +
                    "                .filter(customer -> !Arrays.asList(\"B\", \"C\").contains(customer.getTown().getRegion())\n" +
                    "                        && Arrays.asList(1, 3).contains(customer.getBonusLevel())\n" +
                    "                        && customer.getType().equals(\"M\"))\n" +
                    "                .collect(Collectors.partitioningBy(Customer::hasSmartTechnology,\n" +
                    "                        Collectors.summingLong(Customer::getEnergyConsumption6To12)))", resultMap5);
            Configuration.instance.logNewLine();
            query++;

            Map<String, Long> resultMap6 = streamQuery.executeSQL12(new ArrayList<>(records));
            logQuery(query, "SELECT region, AVG(energyConsumption6To12) FROM data\n" +
                    "    WHERE region IN ('A', 'C')\n" +
                    "    AND type IN ('L', 'M')\n" +
                    "    AND hasSmartTechnology = 'false'\n" +
                    "    GROUP BY region", "customers.stream()\n" +
                    "                .filter(customer -> Arrays.asList(\"A\", \"C\").contains(customer.getTown().getRegion())\n" +
                    "                        && Arrays.asList(\"L\", \"M\").contains(customer.getType())\n" +
                    "                        && !customer.hasSmartTechnology())\n" +
                    "                .collect(Collectors.groupingBy(customer -> customer.getTown().getRegion(),\n" +
                    "                        Collectors.averagingInt(Customer::getEnergyConsumption6To12)))\n" +
                    "                .entrySet()\n" +
                    "                .stream()\n" +
                    "                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().longValue()))", resultMap6);

            Configuration.instance.logNewLine();
            log("### Finished executing stream queries ###");
            System.out.println();
            System.out.println("These results can be found in the file " + Configuration.instance.logFile + ".");
            Configuration.instance.closeLogger();
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

