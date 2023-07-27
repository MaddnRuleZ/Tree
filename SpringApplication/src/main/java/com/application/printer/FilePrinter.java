package com.application.printer;

import com.application.exceptions.UnknownElementException;
import com.application.tree.elements.roots.Root;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class FilePrinter extends Printer {
    private final File currentFile;
    private final Root root;

    public FilePrinter(String currentFile, Root root) {
        this.currentFile = new File(currentFile);
        this.root = root;
    }

    public void export() throws IOException, UnknownElementException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        this.root.toLaTeX(map, "root", 0);
        for(String key : map.keySet()){
            File tempFile = File.createTempFile(key, "txt");
            Files.writeString(tempFile.toPath(), map.get(key));
            Files.move(tempFile.toPath(), currentFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            tempFile.delete();
        }
    }
}
