package com.application.printer;

import com.application.exceptions.UnknownElementException;
import com.application.tree.elements.roots.Input;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * printer for exporting the tree to a file
 */
public class FilePrinter extends Printer {

    /**
     * @param path file to export to
     * @param root tree structure to be exported
     */
    public FilePrinter(String path, Root root) {
        super(path, root);
    }

    /**
     * exports the tree structure to a file
     * @throws IOException if an error occurs while writing to the file
     * @throws UnknownElementException if an unknown element is found
     */
    public void export() throws IOException, UnknownElementException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put(this.getPath(), new StringBuilder());
        this.getRoot().toLaTeX(map, this.getPath(), LaTeXTranslator.INIT_INDENTATION_LEVEL);
        for(String key : map.keySet()){
            if(key == null) {
                throw new UnknownElementException(null, "File Path");
            }
            File tempFile = File.createTempFile(key, "tex");
            Files.writeString(tempFile.toPath(), map.get(key));
            Path currentPath = Path.of(key);
            Files.move(tempFile.toPath(), currentPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            tempFile.delete();
        }
    }
}
