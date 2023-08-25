package SpringApplication.GitTests;

import com.application.exceptions.OverleafGitException;
import com.application.printer.GitPrinter;
import com.application.User;
import org.junit.jupiter.api.Test;

public class GitTestCases {
    private final String workingDirectory = "src/test/java/SpringApplication/GitTests/GitTesting";
    private final String overleafConnectionString = "https://git.overleaf.com/64b430167d4b3be6afb4389c";
    private User user;

    /*
     * Don't Worry this Password is never used
     */
    @Test
    public void gitPullCloneTest() throws OverleafGitException {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", workingDirectory, user);
        gitPrinter.pullOrCloneRepository();
    }

    @Test
    public void gitCommitPushTest() throws OverleafGitException {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", workingDirectory, user);
        gitPrinter.commitAndPush();
    }

    @Test
    public void gitPullTest() throws OverleafGitException {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", workingDirectory, user);
            gitPrinter.pullRepository();
    }

    @Test
    public void isRemoteChanged() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", workingDirectory, user);
        System.out.println(gitPrinter.isRemoteChanged());
    }
}
