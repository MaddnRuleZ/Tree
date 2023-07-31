package com.application.tree.elements.parent;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
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
    private final List<String> captions;
    public static final String CAPTION_IDENT = "\\caption";
    public static final String GRAPHICS_IDENT = "\\includegraphics";
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

    /**
     * if the String Contains a Part of the Graphic-string extract it,
     * get everything in between "[" and "}"
     *
     * @param graphicsString raw graphic string to parse
     */
    public boolean setGraphics(String graphicsString) {
        if (!graphicsString.contains(GRAPHICS_IDENT)) {
            return false;
        }

        String regex = "\\[(.*?)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(graphicsString);

        if (matcher.find()) {
            this.graphic = matcher.group(1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * check if the line is a caption, if so add it to the Figure and return true
     *
     * @param caption current Line to check if Caption
     * @return true if caption was added
     */
    public boolean addCaption(String caption) {
        if (caption.contains(CAPTION_IDENT)) {
            this.captions.add(caption);
            return true;
        }
        return false;
    }


    public void toLaTeX(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        super.toLaTeXStart(map, key, level);
        String indentationHead = getIndentation(level);
        String indentationBody = getIndentation(level+1);

        StringBuilder text = map.get(key);

        text.append(indentationHead).append(this.getStartPart()).append("{").append(this.content).append("}");
        text.append("\n");

        for (String caption : captions) {
            text.append(indentationBody).append(caption);
            text.append("\n");
        }

        text.append(indentationBody).append(GRAPHICS_IDENT).append("{").append(this.graphic).append("}");
        text.append("\n");

        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key, level + 1);
            }
        }

        text.append(indentationHead).append(this.getEndPart());

        super.toLaTeXEnd(map, key, level+1);
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

    @Override
    public String getStartPart() {
        return ElementConfig.FIGURE.getStartPart();
    }

    @Override
    public String getEndPart() {
        return ElementConfig.FIGURE.getEndPart();
    }



}