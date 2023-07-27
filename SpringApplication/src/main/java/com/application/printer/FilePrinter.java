package com.application.printer;

import com.application.exceptions.UnknownElementException;
import com.application.tree.elements.roots.Input;
import com.application.tree.elements.roots.Root;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * printer for exporting the tree to a file
 */
public class FilePrinter extends Printer {
    /**
     * file to export to
     */
    private final File currentFile;
    /**
     * tree structure to be exported
     */
    private final Root root;

    /**
     * @param currentFile file to export to
     * @param root tree structure to be exported
     */
    public FilePrinter(String currentFile, Root root) {
        this.currentFile = new File(currentFile);
        this.root = root;
    }

    /**
     * exports the tree structure to a file
     * @throws IOException if an error occurs while writing to the file
     * @throws UnknownElementException if an unknown element is found
     */
    public void export() throws IOException, UnknownElementException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put("root", new StringBuilder());
        this.root.toLaTeX(map, "root", 0);
        for(String key : map.keySet()){
            if(key == null) {
                throw new UnknownElementException(Input.class.getSimpleName(), key);
            }
            File tempFile = File.createTempFile(key, "tex");
            Files.writeString(tempFile.toPath(), map.get(key));
            Files.move(tempFile.toPath(), currentFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            tempFile.delete();
        }
    }
}
