package SpringApplication.CommandTests.Execution.SpringTests;

import com.application.Application;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.io.File;
import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@ComponentScan(basePackages = "com.application")
@SpringJUnitConfig
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @CsvSource({
            "src/test/resources/JsonFiles/LoadFromFolderTest_1.json, 0",
            "src/test/resources/JsonFiles/NotEnoughParamsCommands/AddElement.json, 1"})
    public void testPostEndpoint(String path, int expectedStatusIndex) throws Exception {
        String jsonContent = loadJsonFile(path);

        ResultMatcher[] statusArray = new ResultMatcher[2];
        statusArray[0] = status().isOk();
        statusArray[1] = status().isBadRequest();

        ResultMatcher expectedStatus = statusArray[expectedStatusIndex];

        mvc.perform(post("/api")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonContent))
                .andExpect(expectedStatus)
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testLoadFullDataGetEndpoint() throws Exception {
        mvc.perform(get("/LoadFullData"))
                .andExpect(status().isOk())
                .andExpect(content()
                    .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.editor").exists());
    }

    @Test
    public void testLoadTreeDataGetEndpoint() throws Exception {
        mvc.perform(get("/LoadTreeData"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tree").exists());
    }

    @Test
    public void testHasChangesEndpoint() throws Exception {
        mvc.perform(get("/checkForUpdates"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.hasUpdates").value(false));
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

