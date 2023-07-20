package com.Application.Interpreter;

import com.Application.Tree.elements.roots.Roots;

/**
 * Class for Parsing a single Latex Document,
 *
 *  input statement in this Doc will trigger recursive ex function
 *
 */
public class Parser {
    private final TextFileReader textFileReader;
    private final String filePath;
    private Scanner scanner;
    private String[] text;

    /**
     *
     * @param filePath
     */
    public Parser(String filePath) {
        this.filePath = filePath;
        this.textFileReader = new TextFileReader(filePath);
    }

    /**
     * Start Parsing the Document, get the Text from the File and
     *
     * @return finished Root, containing the Tree as Children
     */
    public Roots startParsing() {
        if (!textFileReader.validateFile()) {
            return null;
        }

        this.text = textFileReader.readLinesFromFile();
        this.scanner = new Scanner(text);
        return scanner.parseDocument();
    }
}
