package SpringApplication.GitTests;

import com.Application.Printer.GitPrinter;
import org.junit.jupiter.api.Test;


public class GitPull {
    private String wokringDirectory = "C:\\Users\\xmadd\\Desktop\\pullTesting";


    @Test
    public void gitPushTest() {
        GitPrinter gitPrinter = new GitPrinter(null,"https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass?", wokringDirectory);
        gitPrinter.pushChanges();
    }


    public void gitPullTest() {

        GitPrinter gitPrinter = new GitPrinter(null,"https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass?","src/GitDirectoryTests/Overleaf_1/");
        gitPrinter.pushChanges();
    }


    public void gitCloneTest() {

        GitPrinter gitPrinter = new GitPrinter(null,"https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass?","src/GitDirectoryTests/Overleaf_1/");
        gitPrinter.pushChanges();
    }

}
