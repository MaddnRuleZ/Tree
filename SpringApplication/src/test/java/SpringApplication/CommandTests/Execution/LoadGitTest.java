package SpringApplication.CommandTests.Execution;

import com.application.User;
import com.application.command.types.LoadFromFolderCommand;
import com.application.command.types.LoadFromGitCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoadGitTest {

    LoadFromGitCommand command;
    User user;
    private String workingDirectory = "src/TestDocuments/gitTesting/";
    private String overleafConnectionString = "https://git.overleaf.com/64b430167d4b3be6afb4389c";


    @BeforeEach
    void setUp() {
        this.user = new User();
        this.command = new LoadFromGitCommand();
        this.command.setUser(user);
    }

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
