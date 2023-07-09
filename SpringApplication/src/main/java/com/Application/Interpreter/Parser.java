package com.Application.Interpreter;

import Tree.Element;
import Tree.elements.parents.Paragraph;
import Tree.elements.parents.Section;

/**
 *
 *
 */
public class Parser {
    // this defines the level of each Element

    private Scanner scanner;
    private TextFileReader textFileReader;
    private String filePath;
    private String[] text;


    public Parser(String filePath) {
        this.filePath = filePath;
        this.textFileReader = new TextFileReader(filePath);

    }


    // ret root
    public Element startParsing() {
        if (!textFileReader.validateFile()) {
            // Err state
            return null;
        }
        this.text = textFileReader.readLinesFromFile();
        this.scanner = new Scanner(text);

        scanner.parseDocument();
        scanner.debugThatShit();
        return null;
        //         return scanner.parseDocument();
    }
}
