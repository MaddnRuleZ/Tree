package com.application.tree.elements.parent;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.childs.BlockElement;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Figure Class
 *
 * The Figure class represents an environment that contains graphical elements with captions in the Parser.
 *
 * The Figure environment typically includes graphics identified by "\\includegraphics" and their corresponding
 * captions identified by "\\caption". The "graphic" field stores the "\\includegraphics" content for this figure.
 *
 * The captions are stored in the "captions" list, which can contain multiple captions associated with the figure.
 */
public class Figure extends Environment {
    public static final String CAPTION_IDENTIFIER = "\\caption";
    public static final String GRAPHICS_IDENTIFIER = "\\includegraphics";
    public static final String GRAPHICS_CONTENT_REGEX = "\\{(.*?)\\}";
    public static final String GRAPHICS_OPTIONS_REGEX = "\\[(.*?)\\]";
    private final List<String> captions;
    private String graphic_options = null;
    private String graphic;

    /**
     * Constructor for creating a new Figure object with the specified startPart, endPart, and level.
     *
     * @param startPart The startPart string that identifies the beginning of the figure environment.
     * @param endPart   The endPart string that identifies the end of the figure environment.
     * @param level     The hierarchical level of the figure environment.
     */
    public Figure(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
        captions = new ArrayList<>();
    }

    // todo enter Env Function to Parse

    /**
     * Add a new TextBlock to the Environment.
     * If there are already child elements in the Environment, the new TextBlock will be added at the same level.
     * Otherwise, it will be added as a child of the Environment.
     *
     * @param line The line to scan for Summary Comment or NewLine to be added to the TextBlock.
     * @return The newly created or existing TextBlockElement where the line is added.
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (this.children.size() == 0) {
            BlockElement block = generateTextBlockAsChild();
            block.addTextBlockToElem(line);
            return block;
        } else {
            BlockElement block = generateTextSameLevel();
            block.addTextBlockToElem(line);
            return block;
        }
    }

    /**
     * if the String Contains a Part of the Graphic-string extract it,
     * get everything in between "[" and "}"
     *
     * @param graphicsString raw graphic string to parse
     */
    public boolean setGraphics(String graphicsString) {
        if (!graphicsString.contains(GRAPHICS_IDENTIFIER)) {
            return false;
        }

        Matcher matcherContent = Pattern.compile(GRAPHICS_CONTENT_REGEX).matcher(graphicsString);
        if (matcherContent.find()) {
            graphic = matcherContent.group(1);

            Matcher matcherOptions = Pattern.compile(GRAPHICS_OPTIONS_REGEX).matcher(graphicsString);
            if (matcherOptions.find()) {
                this.graphic_options = matcherOptions.group(1);
            }
            return true;
        }
        return false;
    }

    /**
     * check if the line is a caption, if so add it to the Figure and return true
     *
     * @param caption current Line to check if Caption
     * @return true if caption was added
     */
    public boolean addCaption(String caption) {
        if (!caption.contains(CAPTION_IDENTIFIER)) {
            return false;
        }

        Matcher matcher = Pattern.compile(CONTENT_REGEX).matcher(caption);
        if (matcher.find()) {
            this.captions.add(matcher.group(1));
            return true;
        }
        return false;
    }


    public void toLaTeX(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        super.toLaTeXStart(map, key, level);
        String indentationBody = getIndentation(level+1);

        StringBuilder text = map.get(key);

        // \caption{caption_content}
        for (String caption : captions) {
            text.append(indentationBody).append(CAPTION_IDENTIFIER).append("{").append(caption).append("}");
            text.append("\n");
        }

        //TODO graphic options???

        // \includegraphics{graphic}
        text.append(indentationBody).append(GRAPHICS_IDENTIFIER).append("{").append(this.graphic).append("}");
        text.append("\n");

        // children
        if (this.children != null && !this.children.isEmpty()) {
            for (Element child : this.children) {
                child.toLaTeX(map, key, level + 1);
            }
        }

        super.toLaTeXEnd(map, key, level);
    }


    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectNode node = super.toJsonEditor();
        node.put("fileLocation", this.graphic);

        if(this.captions != null && !this.captions.isEmpty()) {
            ArrayNode captionNode = JsonNodeFactory.instance.arrayNode();
            for(String caption : this.captions) {
                node.put("content", caption);
            }
            node.set("captions", captionNode);
        } else {
            node.set("captions", null);
        }
        return node;
    }
}
