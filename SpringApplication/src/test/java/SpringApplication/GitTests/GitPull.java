package SpringApplication.GitTests;

import com.Application.Printer.GitPrinter;
import org.junit.jupiter.api.Test;

public class GitPull {
    private String wokringDirectory = "src/TestDocuments/gitTesting/";
    @Test
    public void gitPushTest() {
        GitPrinter gitPrinter = new GitPrinter("https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory);
        gitPrinter.pushChanges();
    }

    @Test
    public void gitPullTest() {
        GitPrinter gitPrinter = new GitPrinter("https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass??",wokringDirectory);
        gitPrinter.pullRepository();
    }

    @Test
    public void gitCloneTest() {
        GitPrinter gitPrinter = new GitPrinter("https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory);
        gitPrinter.cloneRepository();
    }


    public void gitRebaseTest() {
        GitPrinter gitPrinter = new GitPrinter("https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass??", wokringDirectory);
        gitPrinter.rebaseChanges("master/origin");
    }



}
