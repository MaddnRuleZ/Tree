package SpringApplication;

import com.Application.Printer.FilePrinter;
import com.Application.Tree.elements.root.Root;
import com.Application.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClockTest {
    User user;

    @BeforeEach
    public void setUp() {
        this.user = new User();
    }

    @Test
    public void testClockStartsWhenPrinterSet() throws IllegalStateException {
        user.setPrinter(new FilePrinter("path", Root.getInstance()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(this.user.getClock().getClockThread().isAlive(), "Clock should be running");
    }
}

