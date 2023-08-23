package SpringApplication.SpringTests;

import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.Application;
import com.application.RequestInterceptor;
import com.application.User;
import com.application.printer.AutoExport;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@ComponentScan(basePackages = "com.application")
@SpringJUnitConfig
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AutoExportTest {


    @Autowired
    private MockMvc mvc;
    @Autowired
    AutoExport autoExport;
    @Autowired
    RequestInterceptor interceptor;

    @Test
    public void testAutoExport() throws Exception {
        load();

        Thread.sleep(interceptor.getTimeThresholdInMilliseconds() * 2);

        assertFalse(autoExport.isFailure());
        assertFalse(interceptor.hasChanges());
        assertFalse(interceptor.hasNoRecentRequests());
    }

    @Test
    public void testWithChanges() throws Exception {
        load();

        assertFalse(autoExport.isFailure());
        assertTrue(interceptor.hasChanges());
        assertFalse(interceptor.hasNoRecentRequests());

        change();
        change();

        Thread.sleep(interceptor.getTimeThresholdInMilliseconds()* 2);

        assertFalse(autoExport.isFailure());
        assertFalse(interceptor.hasChanges());
        assertFalse(interceptor.hasNoRecentRequests());
    }

    private void load() throws Exception {

        String jsonContent = loadJsonFile("src/test/resources/JsonFiles/LoadFromFolderTest_1.json");

        mvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

    }


    private void change() throws Exception {
        String jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/AddElement.json");

        mvc.perform(post("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isBadRequest());

    }

    /**
     * Helper method to load JSON file content as a String
     * @param filePath the path of the JSON file
     */
    private String loadJsonFile(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filePath);
        try {
            return mapper.readTree(file).toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
