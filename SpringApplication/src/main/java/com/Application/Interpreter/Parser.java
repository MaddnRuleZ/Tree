package com.Application.Interpreter;

import com.Application.Exceptions.FileInvalidException;
import com.Application.Tree.elements.roots.Roots;

/**
 * Class for Parsing a single Latex Document,
 *
 * input statement in this Doc will trigger recursive ex function
 */
public class Parser {
    private final TextFileReader textFileReader;

    /**
     * generate a new Parser for Parsing a LateX document
     *
     * @param filePath The file path of the text file to be parsed.
     */
    public Parser(String filePath) {
        this.textFileReader = new TextFileReader(filePath);
    }

    /**
     * Start Parsing the Document
     * get the Text from the File and call the Scanner for Scanning the Doc
     *
     * @return finished Root, containing the full Tree as Children
     */
    public Roots startParsing() {
        try {
            if (!textFileReader.validateFile()) {
                return null;
            }
            String[] text = textFileReader.readLinesFromFile();
            Scanner scanner = new Scanner(text);
            return scanner.parseDocument();

        } catch (FileInvalidException exception) {
            return null;
        }
    }
}
