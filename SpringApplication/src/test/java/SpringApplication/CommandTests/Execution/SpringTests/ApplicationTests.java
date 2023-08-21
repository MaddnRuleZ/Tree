package SpringApplication.CommandTests.Execution.SpringTests;

import com.application.Application;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;


public class ApplicationTests {

	@Test
	public void testMainMethod() {
		String[] args = {};

		try {
			Application.main(args);
		} catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		}
	}

}
