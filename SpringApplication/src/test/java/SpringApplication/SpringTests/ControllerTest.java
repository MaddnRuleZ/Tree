package SpringApplication.SpringTests;

// write a test class for the Rest Controller of a SpringApplication, it has to perform a componentScan of the package com.application
// and it has to use the annotation @SpringBootTest
// the test class has to be annotated with @RunWith and @AutoConfigureMockMvc
// the test class has to have a private MockMvc field, autowired
// the test class has to have a setUp method, annotated with @Before, that runs the SpringApplication with the method run of the SpringApplication class and with null as arguments (null is the default value for the arguments of the method run)
// the test class has to have a test method that performs a post request to the endpoint /api/process
// the test method has to use the method perform of the MockMvc field, and it has to use the method post of the MockMvcRequestBuilders class
// the test method has to use the method contentType of the MockHttpServletRequestBuilder class, and it has to use the method APPLICATION_JSON of the MediaType class
// the test method has to use the method content of the MockHttpServletRequestBuilder class, and it has to use the method loadJsonFile of the test class
// the test method has to use the method andExpect of the ResultActions class, and it has to use the method isOk of the ResultMatcher class


import SpringApplication.TestStubs.ComplexTestTree;
import SpringApplication.TestStubs.TestTree;
import com.application.Application;
import com.application.User;
import com.application.exceptions.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@ComponentScan(basePackages = "com.application")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testPostEndpoint() throws Exception {
        String jsonContent = loadJsonFile("src/test/resources/JsonFiles/CorrectCommands/AddElement.json");

        mvc.perform(post("/api")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testLoadFullDataGetEndpoint() throws Exception {
        mvc.perform(get("/LoadFullData")).andExpect(status().isOk());
    }

    @Test
    public void testLoadTreeDataGetEndpoint() throws Exception {
        mvc.perform(get("/LoadTreeData")).andExpect(status().isOk());
    }

    @Test
    public void testHasChangesEndpoint() throws Exception {
        MvcResult result = mvc.perform(get("/checkForUpdates"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        boolean hasUpdates = jsonNode.get("hasUpdates").asBoolean();
        assertFalse(hasUpdates);
    }

    @AfterEach
    void tearDown() {
        mvc = null;
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

