package com.application.printer;

import com.application.User;
import com.application.exceptions.UnknownElementException;
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
     */
    public FilePrinter(String path, User user) {
        super(path, user);
        setFigurePath(Path.of(path).getParent().toString());
    }

    /**
     * exports the tree structure to a file
     * @throws IOException if an error occurs while writing to the file
     * @throws UnknownElementException if an unknown element is found
     */
    public void export() throws IOException, UnknownElementException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put(this.getPath(), new StringBuilder());
        this.getUser().getRoot().toLaTeX(map, this.getPath(), LaTeXTranslator.INIT_INDENTATION_LEVEL, this.isExportSummary(), this.isExportComments());
        for (String key : map.keySet()) {
            if (key == null) {
                throw new UnknownElementException(null, "File Path");
            }
            File tempFile = File.createTempFile(key, "tex");
            Files.writeString(tempFile.toPath(), map.get(key));

            //TODO -----------Remove---------------
            // Path currentPath = Path.of(key);
            Path currentPath = Path.of(randomFilePath());
            //-------------------------------------


            Files.move(tempFile.toPath(), currentPath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            tempFile.delete();
        }
    }

    // -------------------------------------------
    // ----------------for testing----------------
    /**
     * counts the number of files that have been created
     */
    private int fileCount = 0;

    /**
     * generates a random file path in the grandparent directory of the current path
     * @return the random file name
     */
    private String randomFilePath() {
        String path = Path.of(this.getPath()).getParent().getParent().toString() + "/PrinterTestOutput/exportFile" + fileCount + ".tex";
        fileCount++;
        return path;
    }
    // -------------------------------------------

}
