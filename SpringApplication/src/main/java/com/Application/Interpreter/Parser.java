package com.Application.Interpreter;

import com.Application.Tree.elements.Root;

/**
 * Class for Parsing a single Latex Document,
 * input statement in this Doc will trigger recursive ex function
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

    public Root startParsing() {
        if (!textFileReader.validateFile()) {
            return null;
        }

        this.text = textFileReader.readLinesFromFile();
        this.scanner = new Scanner(text);
        return scanner.parseDocument();
    }
}
