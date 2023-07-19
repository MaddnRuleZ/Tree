package com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class Summary extends AdditionalInformationContainer {
    private static final String START_SUMMARY = "%\\start{summary}";
    private static final String END_SUMMARY = "%\\finish{summary}";

    private List<String> summary;

    /**
     *
     *
     */
    public Summary() {
        super();
    }

    /**
     * remove one summary form the text, save summary in this Class,
     * return the rest of the Text
     *
     * @param text
     * @return text w!/ the summaries
     */
    public List<String> extractInfo(List<String> text) {
        List<String> restText = new ArrayList<>();
        boolean insideSummary = false;

        for (String line : text) {
            if (line.contains(START_SUMMARY)) {
                insideSummary = true;
                content = new ArrayList<>();
                content.add(line);

            } else if (line.contains(END_SUMMARY)) {
                if (!insideSummary) {
                    return null; // Err, endsummary b4 startSummary
                }

                insideSummary = false;
                content.add(line);
            } else if (insideSummary) {
                content.add(line);
            } else {
                restText.add(line);
            }
        }
        return restText;
    }
}