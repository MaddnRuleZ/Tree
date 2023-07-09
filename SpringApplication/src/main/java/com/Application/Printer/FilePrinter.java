package com.Application.Printer;

import com.Application.Printer.Printer;
import com.Application.Tree.elements.Root;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class FilePrinter extends Printer {
    private final File currentFile;
    private final Root root;

    public FilePrinter(String currentFile, Root root) {
        this.currentFile = new File(currentFile);
        this.root = root;
    }

    public boolean exportToFile(Root root) {
        try {
            String text = toLaTeX(this.root);
            File tempFile = createTempFile();
            Files.writeString(tempFile.toPath(), text);
            Files.move(tempFile.toPath(), currentFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // TODO Fehlerbehandlung
            return false;
        }
        return true;
    }

    public File createTempFile() {
        String tempPath = this.currentFile.getParent();
        return new File(tempPath + File.pathSeparator + "tempFile" );
    }


}
