import java.util.Map;
import java.util.List;
import java.util.Map;

public interface IQuery {

    long executeSQL01(List<Customer> customers);

    long executeSQL02(List<Customer> customers);

    long executeSQL03(List<Customer> customers);

    long executeSQL04(List<Customer> customers);

    List<Integer> executeSQL05(List<Customer> customers);

    List<Integer> executeSQL06(List<Customer> customers);

    Map<Boolean, Long> executeSQL07(List<Customer> customers);

    Map<String, Long> executeSQL08(List<Customer> customers);

    Map<Integer, Long> executeSQL09(List<Customer> customers);

    Map<String, Long> executeSQL10(List<Customer> customers);

    Map<Boolean, Long> executeSQL11(List<Customer> customers);

    Map<String, Long> executeSQL12(List<Customer> customers);
}