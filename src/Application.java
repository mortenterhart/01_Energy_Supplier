import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Application {
    private List<Customer> records = new ArrayList<>();

    public List<Customer> loadRecords() {
        List<Customer> recordList;
        try {
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    // count
    public void executeSQL01(/*List<Customer> records*/) {
        //System.out.println(records.stream().collect(Collectors.summarizingInt(p->((Integer)p))));
        List<Integer> values = new ArrayList<>();
                   values.add(1);
                   values.add(2);
                   values.add(3);
                   values.add(4);
                   values.add(5);
        System.out.println("Anzahl der List ist:");
        System.out.println(values.stream().count());
        System.out.println(values.size());
    }

    // count, where
    public void executeSQL02() {
    }

    // count, where, in
    public void executeSQL03() {
    }

    // count, where, not in
    public void executeSQL04() {
    }

    // id, where, in, order by desc limit
    public void executeSQL05() {
    }

    // id, where, in, order by desc, order by asc
    public void executeSQL06() {
    }

    // count, group by
    public void executeSQL07() {
    }

    // count, where, group by
    public void executeSQL08() {
    }

    // count, where, in, group by
    public void executeSQL09() {
    }

    // count, where, not in, group by
    public void executeSQL10() {
    }

    // sum, where, not in, in, group by
    public void executeSQL11() {
    }

    // avg, where, in, in, group by
    public void executeSQL12() {
    }

    public void execute() {
        loadRecords();
        executeSQL01();
        executeSQL02();
        executeSQL03();
        executeSQL04();
        executeSQL05();
        executeSQL06();
        executeSQL07();
        executeSQL08();
        executeSQL09();
        executeSQL10();
        executeSQL11();
        executeSQL12();
    }

    public static void main(String... args) {
        Application app = new Application();
        app.execute();
    }
}
