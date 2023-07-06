package Tree;

import Tree.additionalInfo.Comment;
import Tree.additionalInfo.NewLine;
import Tree.additionalInfo.Summary;
import Tree.elements.childs.Equation;
import Tree.elements.parents.Paragraph;
import Tree.elements.parents.Part;
import Tree.elements.parents.Section;
import interpreter.TextFileReader;
import Tree.interfaces.JsonParser;

import java.util.List;
import java.util.UUID;

public abstract class Element implements JsonParser {
    // Halb -ordnungs Level
    private static final Element[] GLOBAL_HALBORDNUNG = {
            new Part(-1), new Section(-1), new Paragraph(-1), new Equation(-1)
    };
    private int level;

    private final Comment comment;
    private final Summary summary;
    private final NewLine newLine;

    private final UUID id;
    private Element parentElement;

    private boolean chooseManualSummary;
    private String manualSummary; // arr?

    private List<Comment> comments;
    private List<NewLine> newLines;
    private List<Summary> summaries;

    private String[] text;
    private String options;

    // rm?
    private final int startIndex;
    private final String startPart;
    private final String endPart;

    public Element(String startPart, String endPart, int startIndex) {
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

    public void generateTextFromIndices(String[] text, int endIndex) {
        if (endIndex != 0) {
            String[] restText;
            restText = TextFileReader.extractStrings(text, this.startIndex, endIndex - 1);

            // by now
            this.text = restText;
            // aufrufen von extract Funktionen auf verbleibendem restText
        }
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

}
