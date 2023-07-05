package main.java.com.Application;

/**
 * holds the tree structure and classes responsible for translating
 * the structure in different formats
 */
public class TreeX {
    /**
     * starting point of the tree structure
     */
    private Root root;
    /**
     * tree structure to LaTeX-Code printer
     */
    private Printer exportPrinter;
    /**
     * LaTeX-Code to tree structure parser
     */
    private ImportParser importParser;

    public Root getRoot() {
        return root;
    }

    public Printer getExportPrinter() {
        return exportPrinter;
    }

    public ImportParser getImportParser() {
        return importParser;
    }
}
