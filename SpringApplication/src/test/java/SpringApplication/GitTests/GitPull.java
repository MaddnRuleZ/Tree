package SpringApplication.GitTests;

import com.application.Printer.GitPrinter;
import org.junit.jupiter.api.Test;

public class GitPull {
    private String wokringDirectory = "src/TestDocuments/gitTesting/";
    private String overleafConnectionString = "https://git.overleaf.com/64b430167d4b3be6afb4389c";

    @Test
    public void gitPushTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory);
        gitPrinter.pushChanges();
    }

    @Test
    public void gitPullTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??",wokringDirectory);
        gitPrinter.pullRepository();
    }

    @Test
    public void gitCloneTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory);
        gitPrinter.cloneRepository();
    }

    @Test
    public void gitRebaseTest() {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory);
        gitPrinter.rebaseChanges("master/origin");
    }

    @Test
    public void checkRemoteChanged() throws Exception {
        GitPrinter gitPrinter = new GitPrinter(overleafConnectionString,
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory);
        System.out.println(gitPrinter.isRemoteRepositoryChanged("master/origin"));
    }
}
