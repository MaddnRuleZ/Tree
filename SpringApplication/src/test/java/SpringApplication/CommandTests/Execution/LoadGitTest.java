package SpringApplication.CommandTests.Execution;

import com.application.User;
import com.application.command.types.LoadFromFolderCommand;
import com.application.command.types.LoadFromGitCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests the execution of an LoadFromGitCommand
 */
public class LoadGitTest {

    /**
     * Command to test
     */
    LoadFromGitCommand command;
    /**
     * User on which the test is executed on
     */
    User user;
    /**
     * Path to the test file
     */
    private String workingDirectory = "src/TestDocuments/gitTesting/";
    /**
     * Path to the test file
     */
    private String overleafConnectionString = "https://git.overleaf.com/64b430167d4b3be6afb4389c";


    /**
     * Sets up the test environment before each test
     */
    @BeforeEach
    void setUp() {
        this.user = new User();
        this.command = new LoadFromGitCommand();
        this.command.setUser(user);
    }

    /**
     * Tests the execution of an LoadFromGitCommand
     * Tests if the content is added correctly
     */
    @Test
    void loadTest() {
        this.command.setUrl(overleafConnectionString);
        this.command.setPath(workingDirectory);
        this.command.setPassword("WhatWasMyPass??");
        this.command.setUsername("ueteb@student.kit.edu");

        this.command.execute();

        assertTrue(command.isSuccess(), "Command should execute successfully");
    }


}
