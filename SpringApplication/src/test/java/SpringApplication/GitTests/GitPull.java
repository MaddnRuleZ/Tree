package SpringApplication.GitTests;

import com.application.printer.GitPrinter;
import com.application.User;
import org.junit.jupiter.api.Test;

public class GitPull {
    private String wokringDirectory = "src/TestDocuments/gitTesting/";
    private String overleafConnectionString = "https://git.overleaf.com/64b430167d4b3be6afb4389c";

    private User user;

    @Test
    public void gitPushTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory, user);
        gitPrinter.pushChanges();
    }

    @Test
    public void gitPullTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??",wokringDirectory, user);
        gitPrinter.pullRepository();
    }

    @Test
    public void gitCloneTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory, user);
        gitPrinter.cloneRepository();
    }

    @Test
    public void gitRebaseTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory, user);
        gitPrinter.rebaseChanges("master/origin");
    }

    @Test
    public void checkRemoteChanged() throws Exception {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory, user);
        System.out.println(gitPrinter.isRemoteRepositoryChanged("master/origin"));
    }
}
