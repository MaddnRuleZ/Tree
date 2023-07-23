package SpringApplication.GitTests;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Printer.GitPrinter;
import org.junit.jupiter.api.Test;


public class GitPull {

    @Test
    public void gitPushTest() throws UnknownElementException {
        /*
         * test the Parser and check if the file in sys.out is like the input_file below >>>
         */

        GitPrinter gitPrinter = new GitPrinter(null,"https://git.overleaf.com/64b430167d4b3be6afb4389c",
                "ueteb@student.kit.edu", "WhatWasMyPass?","src/GitDirectoryTests/");

        gitPrinter.cloneOrUpdateRepository();
    }
}
