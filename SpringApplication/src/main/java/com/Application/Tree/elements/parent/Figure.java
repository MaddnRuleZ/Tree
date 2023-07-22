package com.Application.Tree.elements.parent;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.childs.BlockElement;
import com.Application.Tree.elements.ElementConfig;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Figure extends Environment {
    private final List<String> captions;
    public static final String CAPTION_IDENT = "\\caption";
    public static final String GRAPHICS_IDENT = "\\includegraphics";
    private String graphic;

    /**
     * Create a new Environment
     *
     * @param startPart
     * @param endPart
     * @param level
     */
    public Figure(String startPart, String endPart, int level) {
        super(startPart, endPart, level);
        captions = new ArrayList<>();
    }


    @Override
    public Element addTextBlockToElem(String line) {
        if (this.childElements.size() == 0) {
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
     * Extract from the content of the Graphic String
     * from a Graphic-string: \includegraphics[insertName]{insertName}
     * get everything in between "[" and "}"
     *
     * @param graphicsString raw graphic string to parse
     */
    protected void setGraphics(String graphicsString) {
        String regex = "\\[(.*?)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(graphicsString);

        if (matcher.find()) {
            this.graphic = matcher.group(1);
        } else {
            System.out.println("Error, Graphic String contains Illegal Format");
            this.graphic = null;
        }
    }

    protected void addCaption(String caption) {
        this.captions.add(caption);
    }


    public void toLaTeX(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\n");

        if(this.summary != null) {
            this.summary.toLaTeX(map, key);
        }
        if(this.comment != null) {
            this.comment.toLaTeX(map, key);
        }

        text.append(this.getStartPart()).append("{").append(this.content).append("}");
        text.append("\n");

        for (String caption : captions) {
            text.append(CAPTION_IDENT).append("{").append(caption).append("}");
            text.append("\n");
        }

        text.append(GRAPHICS_IDENT).append("{").append(this.graphic).append("}");
        text.append("{").append(this.graphic).append("}");
        text.append("\n");

        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key);
            }
        }

        text.append(this.getEndPart());

        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key);
        }

        text.append("\n");
    }


    //TODO captions.toString
    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectNode node = super.toJsonEditor();
        node.put("fileLocation", this.graphic);

        if(this.captions != null && !this.captions.isEmpty()) {
            node.put("captions", this.captions.toString());
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
