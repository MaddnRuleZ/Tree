package main.java.com.Application.Tree.additionalInfo;

import java.util.ArrayList;
import java.util.List;

public class Summary extends AdditionalInformationContainer{
    private static final String START_SUMMARY = "%\\begin{summary}";
    private static final String END_SUMMARY = "%\\end{summary}";
    private List<String> summary;

    /**
     *
     */
    public Summary() {

    }


    /**
     * remove one summary form the text, return the rest of the Text
     *
     * @param remainText
     * @return text w!/ the summaries
     */
    public List<String> extractSummary(List<String> remainText) {
        summary = new ArrayList<>();

        List<String> restText = new ArrayList<>();
        boolean insideSummary = false;

        for (String line: remainText) {
            if (line.contains(START_SUMMARY)) {
                insideSummary = true;
                summary = new ArrayList<>();
                summary.add(line);

            } else if (line.contains(END_SUMMARY)) {
                if (!insideSummary) {
                    // Err, endsummary b4 startSummary
                    return null;
                }

                insideSummary = false;
                summary.add(line);
            } else if (insideSummary) {
                summary.add(line);
            } else {
                restText.add(line);
            }
        }
        this.summary = summary;
        return restText;
    }

    /**
     * // this could be used for multiple Summaries in 1; mb
     *
     *
     * remove all handwritten summaries form the text
     *
     * @param remainText
     * @return text w!/ the summaries
     */
    public List<String> searchForSummaries(List<String> remainText) {
        List<List<String>> parts = new ArrayList<>();
        List<String> nonSummaryText = new ArrayList<>();
        List<String> currentPart = null;
        this.summary = new ArrayList<>();

        boolean insideSummary = false;

        for (String line: remainText) {
            if (line.contains(START_SUMMARY)) {
                insideSummary = true;
                currentPart = new ArrayList<>();
                currentPart.add(line);

            } else if (line.contains(END_SUMMARY)) {
                if (currentPart == null) {
                    // Err state, falsly end sumarry
                    return null;
                }

                insideSummary = false;
                currentPart.add(line);
                parts.add(currentPart);

            } else if (insideSummary) {
                currentPart.add(line);

            } else {
                nonSummaryText.add(line);
            }
        }

        //this.summaries = parts;
        return nonSummaryText;
    }


    public List<String> getSummary() {
        return summary;
    }

}
