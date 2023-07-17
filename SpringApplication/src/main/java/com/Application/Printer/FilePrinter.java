package com.Application.Printer;

import com.Application.Tree.elements.Root;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FilePrinter extends Printer {
    private final File currentFile;
    private final Root root;

    public FilePrinter(String currentFile, Root root) {
        this.currentFile = new File(currentFile);
        this.root = root;
    }

    public boolean export() throws IOException {
        List<String> text = this.root.toText();
        File tempFile = createTempFile();
        // Files.writeString(tempFile.toPath(), text); TODO konvetierung von Listen zu String
        Files.move(tempFile.toPath(), currentFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return true;
    }

    public File createTempFile() {
        String tempPath = this.currentFile.getParent();
        return new File(tempPath, generateRandomName());
    }

    /**
     * generates a random name for the temp file
     * @return
     */
    public String generateRandomName() {
        String ext = "tex";
        // TODO
        return null;
    }
}
