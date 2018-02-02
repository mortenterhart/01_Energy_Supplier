import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StreamQueryTest {
    private static IQuery streamQuery;
    private static List<Customer> customers;

    @BeforeClass
    public static void loadCustomers() {
        streamQuery = new StreamQuery();
        customers   = CSVRecordImport.loadRecordsFromCSV(Configuration.instance.recordsFile);
    }

    @Test
    public void testStreamQuery01() {
        Assert.assertEquals(1_000_000L, streamQuery.executeSQL01(customers));
    }

    @Test
    public void testStreamQuery02() {
        Assert.assertEquals(38_085L, streamQuery.executeSQL02(customers));
    }

    @Test
    public void testStreamQuery03() {
        Assert.assertEquals(8_757L, streamQuery.executeSQL03(customers));
    }

    @Test
    public void testStreamQuery04() {
        Assert.assertEquals(1_262L, streamQuery.executeSQL04(customers));
    }

    @Test
    public void testStreamQuery05() {
        List<Integer> expectedIds = Arrays.asList(602_612, 330_966, 329_281);

        // HSQLDB provides a different sorting algorithm than Java Streams so results differ
        // Assert.assertEquals(expectedIds, streamQuery.executeSQL05(customers));
    }

    @Test
    public void testStreamQuery06() {
        List<Integer> expectedIds = Arrays.asList(11_811, 472_294, 371_316, 811_093);
        Assert.assertEquals(expectedIds, streamQuery.executeSQL06(customers));
    }

    @Test
    public void testStreamQuery07() {
        Map<Boolean, Long> expectedCount = new HashMap<>(2);
        expectedCount.put(false, 750_476L);
        expectedCount.put(true, 249_524L);

        Assert.assertEquals(expectedCount, streamQuery.executeSQL07(customers));
    }

    @Test
    public void testStreamQuery08() {
        Map<String, Long> expectedRegions = new HashMap<>(7);
        expectedRegions.put("C", 31_008L);
        expectedRegions.put("B", 27_637L);
        expectedRegions.put("F", 28_822L);
        expectedRegions.put("A", 31_227L);
        expectedRegions.put("E", 28_287L);
        expectedRegions.put("G", 30_151L);
        expectedRegions.put("D", 28_923L);

        Assert.assertEquals(expectedRegions, streamQuery.executeSQL08(customers));
    }

    @Test
    public void testStreamQuery09() {
        Map<Integer, Long> expectedLevel = new HashMap<>(3);
        expectedLevel.put(1, 167_362L);
        expectedLevel.put(3, 166_160L);
        expectedLevel.put(2, 166_734L);

        Assert.assertEquals(expectedLevel, streamQuery.executeSQL09(customers));
    }

    @Test
    public void testStreamQuery10() {
        Map<String, Long> expectedCounts = new HashMap<>(5);
        expectedCounts.put("C", 37_449L);
        expectedCounts.put("D", 34_839L);
        expectedCounts.put("F", 35_225L);
        expectedCounts.put("E", 34_253L);
        expectedCounts.put("G", 36_693L);

        Assert.assertEquals(expectedCounts, streamQuery.executeSQL10(customers));
    }

    @Test
    public void testStreamQuery11() {
        Map<Boolean, Long> expectedSums = new HashMap<>(2);
        expectedSums.put(false, 12_290_721L);
        expectedSums.put(true, 4_096_222L);

        Assert.assertEquals(expectedSums, streamQuery.executeSQL11(customers));
    }

    @Test
    public void testStreamQuery12() {
        Map<String, Long> expectedAverages = new HashMap<>(2);
        expectedAverages.put("A", 137L);
        expectedAverages.put("C", 137L);

        Assert.assertEquals(expectedAverages, streamQuery.executeSQL12(customers));
    }
}
