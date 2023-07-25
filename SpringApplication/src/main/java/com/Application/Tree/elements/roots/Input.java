package com.Application.Tree.elements.roots;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.elements.parent.Parent;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 */
public class Input extends Parent implements Roots {
    private static final String startPart = "\\input";
    private static final String INPUT_REGEX_FORMAT = "\\\\input\\{([^}]*)\\}";

    /**
     * Input Constructor, call Constructor of Parent
     */
    public Input() {
        super(startPart, null, 1);
    }

    /**
     * extract the Document path from an input command in the Latex File
     * \\input{<filePath>}#
     *
     * @param pathLine path in the Document
     * @return extracted Document File Path
     */
    public static String extractPathRegex(String pathLine) {
        String path;
        Pattern pattern = Pattern.compile(INPUT_REGEX_FORMAT);
        Matcher matcher = pattern.matcher(pathLine);
        if (matcher.find()) {
            path = matcher.group(1);
        } else {
            throw new IllegalArgumentException("Error, couldn't parse the path");
        }
        return path;
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level) throws UnknownElementException {
        super.toLaTeXStart(map, key, level);
        StringBuilder text = map.get(key);

        text.append(this.getStartPart()).append("{").append(this.content).append("}");

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key, level);
        }

        String newKey = this.content;
        map.put(newKey, new StringBuilder());
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, newKey, 0);
            }
        }
        super.toLaTeXEnd(map, key, level);
    }

    public String getStartPart() {
        return ElementConfig.INPUT.getStartPart();
    }
}
