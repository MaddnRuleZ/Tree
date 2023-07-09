package main.java.com.Application.Tree;

import main.java.com.Application.Tree.additionalInfo.Comment;
import main.java.com.Application.Tree.additionalInfo.NewLine;
import main.java.com.Application.Tree.additionalInfo.Summary;
import main.java.com.Application.Tree.elements.parents.environments.Algorithm;
import main.java.com.Application.Tree.elements.parents.environments.Equation;
import main.java.com.Application.Tree.elements.parents.environments.Figure;
import main.java.com.Application.Tree.elements.parents.sectioning.*;
import main.java.com.Application.Tree.interfaces.JsonParser;

import java.util.*;

public abstract class Element implements JsonParser {

    private final Map<Class<?>,Integer> SEMI_ORDER_LEVEL_MAP;

    // outdated
    private static final Element[] GLOBAL_HALBORDNUNG = {
            new Part(-1),
            new Chapter(-1),
            new Section(-1),
            new SubSection(-1),
            new SubSubSection(-1),
            new Paragraph(-1),
            new SubParagraph(-1),
            new Equation(-1)
    };


    private Map<Class<?> ,Integer> createLevelMap() {
        Map<Class<?> ,Integer> levelMap = new HashMap<>();

        levelMap.put(Part.class, 0);
        levelMap.put(Chapter.class, 1);
        levelMap.put(Section.class, 2);
        levelMap.put(SubSection.class, 3);
        levelMap.put(SubSubSection.class, 4);
        levelMap.put(Paragraph.class, 5);
        levelMap.put(SubParagraph.class, 6);

        levelMap.put(Equation.class, 7);
        levelMap.put(Algorithm.class, 7);
        levelMap.put(Figure.class, 7);

        return levelMap;
    }

    private final UUID id;
    private int level;
    private Element parentElement;
    private String[] text;
    private final Comment comment;
    private final Summary summary;
    private final NewLine newLine;
    private boolean chooseManualSummary;
    private List<String> comments;
    private List<NewLine> newLines;     // Parts rename?

    private Summary exSummary;

    private String options;

    // additional Necc
    private final int startIndex;
    private final String startPart;
    private final String endPart;

    public Element(String startPart, String endPart, int startIndex) {
        this.SEMI_ORDER_LEVEL_MAP = createLevelMap();
        this.id = UUID.randomUUID();

        this.startPart = startPart;
        this.endPart = endPart;
        this.startIndex = startIndex;

        comment = new Comment();
        summary = new Summary();
        newLine = new NewLine();
        setElementLevel();

    }

    /**
     * set all static Level of the Elements
     *
     */
    private void setElementLevel() {
        /*
        for (Map.Entry<Element, Integer> entry : createLevelMap().entrySet()) {
            Element element = entry.getKey();
            Integer level = entry.getKey();
            element.setElementLevel();
        }
        */

            // -1 markes dummys cause coldstart
        if (startIndex == -1) {
            return;
        }

        for (int i = 0; i < GLOBAL_HALBORDNUNG.length; i++) {
            if (this.startPart.equals(GLOBAL_HALBORDNUNG[i].getStartPart())) {
                this.level = i;
            }
        }
    }


    /**
     * Read the contents of the structure Element
     *
     *
     * @param text
     * @param endIndex
     */
    // mini Parser For Comments, Summaries and NewLines, okay by now resorted to c,s,p. Max: nC, 1S nP
    public void generateTextFromIndices(String[] text, int endIndex) {

        if (endIndex != 0) {
            options = extractOptionsString(text[this.startIndex]);
            String[] elementFullText = TextFileReader.extractStrings(text, this.startIndex + 1, endIndex - 1);
            this.text = elementFullText;
            List<String> remainingText = Arrays.stream(elementFullText).toList();

            // unclean !
            remainingText = summary.extractSummary(remainingText);
            remainingText = comment.extractComments(remainingText);
            newLine.extractTextParts(remainingText);
            // extract

        }
    }


    private String extractOptionsString(String rawOptions) {
        // do Regex to extract the Options String Parts
        return rawOptions;
    }

    public void setParent(Element parentElement) {
        this.parentElement = parentElement;
    }
    public Element getParentElement() {
        return parentElement;
    }

    public String getStartPart() {
        return startPart;
    }

    public String getEndPart() {return endPart;}

    public String toJson() {
        return null;
    }
    public String[] getText() {
        return this.text;
    }
    public UUID getId() {
        return this.id;
    }
    public int getLevel() {
        return level;
    }

    /**
     * add child if not Child itself
     *
     * @param child element to add
     */
    public abstract void addChild(Element child);

    public boolean isChooseManualSummary() {
        return chooseManualSummary;
    }

    public void setChooseManualSummary(boolean chooseManualSummary) {
        this.chooseManualSummary = chooseManualSummary;
    }







}
