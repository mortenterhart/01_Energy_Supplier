import java.util.List;
import java.util.Map;

public interface IStreamQuery {
    void executeSQL01(List<Customer> customers);

    void executeSQL02(List<Customer> customers);

    void executeSQL03(List<Customer> customers);

    void executeSQL04(List<Customer> customers);

    List<Integer> executeSQL05(List<Customer> customers);

    List<Integer> executeSQL06(List<Customer> customers);

    void executeSQL07(List<Customer> customers);

    void executeSQL08(List<Customer> customers);

    void executeSQL09(List<Customer> customers);

    void executeSQL10(List<Customer> customers);

    Map<Boolean, Integer> executeSQL11(List<Customer> customers);

    Map<String, Integer> executeSQL12(List<Customer> customers);
}
