package main.java.com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

public class NewLine extends AdditionalInformationContainer{

    // catch too?
    private final static String EMPTYLINE = "";
    private final static String NEW_LN_CHARACTER = "\\newline";
    private final static String NEW_LN_CHARACTER1 = "\\\\";
    private List<List<String>> textParts = new ArrayList<>();

    private final List<String> lines;


    public NewLine() {
        lines = new ArrayList<>();
    }

    public void extractTextParts(List<String> remainText) {
        List<List<String>> parts = new ArrayList<>();
        ArrayList<String> currentPart = new ArrayList<>();

        for (String line: remainText) {
            if (line.contains(NEW_LN_CHARACTER) || line.contains(NEW_LN_CHARACTER1)) {
                parts.add(currentPart);
                currentPart = new ArrayList<>();
            }
            currentPart.add(line);
        }
        textParts = parts;
    }

    public List<List<String>> getLines() {
        return textParts;
    }
}
