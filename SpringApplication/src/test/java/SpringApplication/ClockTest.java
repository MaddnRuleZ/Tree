package SpringApplication;
import com.Application.Application;
import com.Application.Printer.Clock;
import com.Application.Printer.FilePrinter;
import com.Application.Printer.Printer;
import com.Application.Tree.elements.Root;
import com.Application.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClockTest {
    User user;

    @BeforeEach
    public void setUp() {
        this.user = new User();
    }

    @Test
    public void testClockStartsWhenPrinterSet() throws IllegalStateException {
        user.setPrinter(new FilePrinter("path", new Root()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(this.user.getClock().getClockThread().isAlive(), "Clock should be running");
    }
}

