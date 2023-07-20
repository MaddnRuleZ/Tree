package com.Application.Tree.elements.parents;

import com.Application.Tree.elements.Element;
import com.Application.Tree.elements.childs.BlockElement;
import com.Application.Tree.elements.parents.Environment;

import java.util.ArrayList;
import java.util.List;
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
}
