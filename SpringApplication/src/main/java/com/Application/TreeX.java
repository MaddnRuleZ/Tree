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
    private ExportPrinter exportPrinter;
    /**
     * LaTeX-Code to tree structure parser
     */
    private ImportParser importParser;

    public TreeX() {
        this.root = Root.getInstance();
    }

    public void setExportPrinter(ExportPrinter exportPrinter) {
        this.exportPrinter = exportPrinter;
    }

    public void setImportParser(ImportParser importParser) {
        this.importParser = importParser;
    }



    public Root getRoot() {
        return root;
    }

    public ExportPrinter getExportPrinter() {
        return exportPrinter;
    }

    public ImportParser getImportParser() {
        return importParser;
    }
}
