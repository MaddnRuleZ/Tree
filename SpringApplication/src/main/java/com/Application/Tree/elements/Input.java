package com.Application.Tree.elements;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.interfaces.Roots;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input extends Parent implements Roots {
    private static final String startPart = "\\input";

    /**
     *
     */
    public Input() {
        super(startPart, null, Root.MIN_LEVEL);
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
    public Element searchForID(UUID id) {
        return null;
    }


    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n");

        if(this.summary != null) {
            this.summary.toLaTeX(map, key);
        }
        if(this.comment != null) {
            this.comment.toLaTeX(map, key);
        }

        text.append(this.getStartPart()).append("{").append(this.content).append("}");

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key);
        }

        String newKey = this.content;
        map.put(newKey, new StringBuilder());
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, newKey);
            }
        }
    }

    public String getStartPart() {
        return ElementConfig.INPUT.getStartPart();
    }
}
