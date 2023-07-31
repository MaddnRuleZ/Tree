package com.application.tree.elements.roots;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.elements.childs.BlockElement;
import com.application.tree.elements.parent.Parent;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Class Represents an Input Statement in the LateX file and is an Element
 * Represents an Input Document inside the main.tex file and Stores all Items of the Document inside it as Children
 *
 */
public class Input extends Parent implements Roots {
    private static final String START_PART = "\\\\input";
    private static final String INPUT_REGEX_FORMAT = START_PART + "\\{([^}]*)\\}";

    /**
     * Input Constructor, call Constructor of Parent
     */
    public Input() {
        super(START_PART, null, ElementConfig.INPUT_DOCUMENT_LEVEL);
    }

    /**
     * extract the Document path from an input command in the Latex File
     * \\input{<filePath>}
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
    public Element addTextBlockToElem(String line) {
        BlockElement block = generateTextSameLevel();
        block.addTextBlockToElem(line);
        return block;
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level) throws UnknownElementException {
        super.toLaTeXStart(map, key, level);
        StringBuilder text = map.get(key);
        String indentation = getIndentation(level);

        text.append(indentation).append(this.getStartPart()).append("{").append(this.content).append("}");

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key, level);
        }

        String newKey = this.content;
        map.put(newKey, new StringBuilder());
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, newKey, INIT_INDENTATION_LEVEL);
            }
        }
        super.toLaTeXEnd(map, key, level);
    }

    public String getStartPart() {
        return ElementConfig.INPUT.getStartPart();
    }
}
