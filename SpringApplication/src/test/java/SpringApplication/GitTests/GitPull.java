package SpringApplication.GitTests;

import com.Application.Printer.GitPrinter;
import org.junit.jupiter.api.Test;


public class GitPull {
    @Test
    public void gitPushTest() throws InterruptedException {
        GitPrinter gitPrinter = new GitPrinter(null,"https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass?","src/GitDirectoryTests/Overleaf_1/");

       // gitPrinter.cloneRepository();

        //Thread.sleep(20000);
        gitPrinter.pushChanges();
    }
}
