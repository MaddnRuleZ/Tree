package com.Application.Interpreter;


import com.Application.Tree.Element;
import com.Application.Tree.elements.sectioning.Root;

/**
 * Class for Parsing a single Latex Document,
 * input statement in this Doc will trigger recursive ex function
 *
 */
public class Parser {
    private Scanner scanner;
    private TextFileReader textFileReader;
    private String filePath;
    private String[] text;

    /**
     *
     * @param filePath
     */
    public Parser(String filePath) {
        Element.createLevelMap();
        this.filePath = filePath;
        this.textFileReader = new TextFileReader(filePath);

    }

    public Root startParsing() {
        if (!textFileReader.validateFile()) {
            // Err state
            return null;
        }
        this.text = textFileReader.readLinesFromFile();
        this.scanner = new Scanner(text);
        return scanner.parseDocument();
    }
}
