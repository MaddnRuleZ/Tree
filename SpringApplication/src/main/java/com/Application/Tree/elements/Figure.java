package com.Application.Tree.elements;

import java.util.ArrayList;
import java.util.List;

public class Figure extends Environment {

    private final List<String> captions;
    private String graphic;


    /**
     * Create a new Environment
     *
     * @param startPart
     * @param endPart
     * @param startIndex
     * @param level
     */
    public Figure(String startPart, String endPart, int startIndex, int level) {
        super(startPart, endPart, startIndex, level);
        captions = new ArrayList<>();
    }

    public void setGraphics(String graphicsString) {
        // todo do RegEx
        this.graphic = graphicsString;
    }

    public void addCaption(String caption) {
        this.captions.add(caption);
    }
}
