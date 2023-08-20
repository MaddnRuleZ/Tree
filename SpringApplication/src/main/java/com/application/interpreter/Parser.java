package com.application.interpreter;

import com.application.exceptions.FileInvalidException;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;

/**
 * Class for recursively Parsing a single Latex Document,
 *
 * input statement in this Doc will trigger recursive call of this Class
 */
public class Parser {
    private final TextFileReader textFileReader;

    /**
     * generate a new Parser for Parsing a LateX document
     * without a File
     * use startParsing(String text) to parse a String
     */
    public Parser() {
        this.textFileReader = null;
    }
    /**
     * generate a new Parser for Parsing a LateX document
     *
     * @param filePath The file path of the text file to be parsed.
     */
    public Parser(String filePath) {
        this.textFileReader = new TextFileReader(filePath);
    }

    /**
     * Call this function when starting the Parsing of a full new Directory
     */
    public void initStartParsing() {
        Root.resetInstance();
    }

    /**
     * Start Parsing the Document
     * get the Text from the File and call the Scanner for Scanning the Doc
     *
     * @return finished Root, containing the full tree as Children, null if Error occurred
     */
    public Roots startParsing() throws FileInvalidException {
        if (!textFileReader.validateFile()) {
            return null;
        }

        String[] text = textFileReader.readLinesFromFile();
        Scanner scanner = new Scanner(text);
        return scanner.parseDocument();
    }

    /**
     * starts Parsing a String
     * @param text the String to be parsed
     * @return finished Root, containing the full tree as Children, null if Error occurred
     */
    public Roots startParsing(String text) {
        String[] lines = text.split("\n");
        Scanner scanner = new Scanner(lines);
        return scanner.parseDocument();
    }
}
