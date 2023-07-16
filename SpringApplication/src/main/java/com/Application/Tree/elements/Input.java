package com.Application.Tree.elements;

import com.Application.Tree.Element;
import com.Application.Tree.interfaces.Roots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input extends Parent implements Roots {
    private static final String startPart = "\\input";
    private static final int LEVEL = 0;

    /**
     *
     */
    public Input() {
        super(startPart, null, 0, LEVEL);
    }

    /**
     * @param pathLine
     * @return
     */
    public static String extractPathRegex(String pathLine) {
        String path;
        String regexPattern = "\\\\input\\{([^}]*)\\}";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(pathLine);
        if (matcher.find()) {
            path = matcher.group(1);
        } else {
            System.out.println("Error, couldn't parse the path");
            return null;
        }
        return path;
    }


    @Override
    public String[] toText() {
        List<String> arrayList = new ArrayList<>();
        String[] curr = this.getText();

        if (curr != null) {
            arrayList.add(this.getOptions());

            if (this.comment.getContent() != null) {
                arrayList.addAll(this.comment.getContent());
            }

            if (this.summary.getContent() != null) {
                arrayList.addAll(this.summary.getContent());
            }

            if (textBlock.getTextParts() != null) {
                for (List<String> list: textBlock.getTextParts()) {
                    if (list != null) {
                        arrayList.addAll(list);
                    }
                }
            }
        }

        for (Element element: childElements) {
            arrayList.addAll(Arrays.asList(element.toText()));
        }
        return arrayList.toArray(new String[0]);
    }

    @Override
    public Element searchForID(UUID id) {
        return null;
    }
}
