package main.java.com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

public class Comment extends AdditionalInformationContainer {
    private final static String START_CHARACTER = "%";
    private List<String> comments;
    public Comment() {

    }

    /**
     * returns the remaining Text
     *
     * @param remainText
     * @return
     */
    public List<String> extractComments(List<String> remainText) {
        ArrayList<String> remainingText = new ArrayList<>();
        this.comments = new ArrayList<>();

        for (String line: remainText) {
            if (line.contains(START_CHARACTER)) {
                this.comments.add(line);
            } else {
                remainingText.add(line);
            }
        }
        return remainingText;
    }

    public List<String> getComments() {
        return this.comments;
    }
}
