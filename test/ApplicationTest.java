import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class ApplicationTest {
    Application application = new Application();

    @Test
    public void testExecuteSQL01() {
        List<Customer> customers = application.loadRecords();
        List<Customer> result = new ArrayList<>(customers);
        application.executeSQL01(customers);
        assertEquals(1_000_000, result.size());
    }
}
