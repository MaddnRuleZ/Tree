package com.Application.Printer;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.elements.roots.Root;

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

    public boolean export() throws IOException {
        try {
            Map<String, StringBuilder> map = new HashMap<>();
            map.put("root", new StringBuilder());
            this.root.toLaTeX(map, "root");
            for(String key : map.keySet()){
                File tempFile = createTempFile();
                Files.writeString(tempFile.toPath(), map.get(key));
                Files.move(tempFile.toPath(), currentFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (UnknownElementException e) {
            //TODO Error Handling
            return false;
        }

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
