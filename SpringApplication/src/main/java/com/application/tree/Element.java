package com.application.tree;

import com.application.exceptions.ParseException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.additionalInfo.Comment;
import com.application.tree.additionalInfo.NewLine;
import com.application.tree.additionalInfo.Summary;
import com.application.tree.elements.childs.BlockElement;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.LaTeXTranslator;
import com.application.tree.interfaces.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public abstract class Element implements JsonParser, LaTeXTranslator {
    private final UUID id;
    private final int level;
    protected Parent parentElement;
    protected List<String> text;
    protected final Summary summary;
    protected final Comment comment;
    protected final NewLine newLine;
    private boolean chooseManualSummary;
    protected String content;
    protected String options;
    private final String startPart;
    private final String endPart;

    protected String type;

    /**
     *
     * @param startPart
     * @param endPart
     * @param level
     */
    public Element(String startPart, String endPart, int level) {
        this.id = UUID.randomUUID();
        this.startPart = startPart;
        this.endPart = endPart;
        this.level = level;
        this.text = new ArrayList<>();

        comment = new Comment();
        summary = new Summary();
        newLine = new NewLine();
        Root.updateLevelCap(level);
    }

    /**
     *
     * @param line
     * @return
     */
    public abstract Element addTextBlockToElem(String line);

    /**
     *
     * @return
     */
    public BlockElement generateTextSameLevel() {
        BlockElement blockElement = new BlockElement();
        parentElement.addChild(blockElement);
        blockElement.setParent(parentElement);
        return blockElement;
    }

    public void setParent(Parent parentElement) {
        this.parentElement = parentElement;
    }
    public Parent getParentElement() {
        return parentElement;
    }
    public String getEndPart() {
        return endPart;
    }

    public String getStartPart() throws UnknownElementException {
        return startPart;
    }

    public List<String> getText() {
        return this.text;
    }
    public UUID getId() {
        return this.id;
    }
    public int getLevel() {
        return level;
    }
    public String getOptions() {
        return this.options;
    }
    public boolean isChooseManualSummary() {
        return chooseManualSummary;
    }
    public void setChooseManualSummary(boolean chooseManualSummary) {
        this.chooseManualSummary = chooseManualSummary;
    }

    public void setOptions(String optionsString) {
        Pattern pattern = Pattern.compile("\\{([^}]*)\\}");
        Matcher matcher = pattern.matcher(optionsString);

        if (matcher.find()) {
            this.options = matcher.group(1);
        } else {
            this.options = null;
        }
    }

    public void setContent(String content) {
        Pattern pattern = Pattern.compile("[\\{\\[](\\w*)[\\}\\]]");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            this.content = matcher.group(1);
        } else {
            this.content = null;
        }
    }

    /**
     * parses the incoming String into comment
     * sets the comment of the calling Element
     * @param comment to parse
     * @throws ParseException if the comment is not valid
     */
    public void setComment(String comment) throws ParseException{
        if (!this.comment.extractContent(comment)) {
           throw new ParseException(comment);
        }
    }

    /**
     * parses the incoming String into summary
     * @param summary
     */
    public void setSummary(String summary) {
       this.summary.setSummary(summary);
    }

    /**
     * calculates the level of the calling Element from bottom to top
     * @return level of the calling Element
     */
    public int calculateLevelFromElement() {
        Parent parent = this.getParentElement();
        if (parent == null) {
            return 0;
        }
        return parent.calculateLevelFromElement();
    }

    /**
     * searches for the element with the given id
     * @param id to search for
     * @return found Element or null
     */
    public abstract Element searchForID(UUID id);

    /**
     * traverses the tree starting from calling Element
     * Passed Sections add 1 to the level
     * @return level of the deepest sectioning child
     */
    public abstract int levelOfDeepestSectioningChild();

    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("id", this.id.toString());
        node.put("type", this.type);

        if(this.getParentElement() == null) {
            node.put("parent", "null");
        } else {
            node.put("parent", this.getParentElement().getId().toString());
        }
        node.put("content", this.content);
        node.put("comment", comment.toString());
        if(!isChooseManualSummary() || summary == null || summary.getSummary() == null) {
            node.put("summary", "null");
        } else {
            node.put("summary", summary.toString());
        }
        node.put("chooseManualSummary", this.chooseManualSummary);

        return node;
    }

    @Override
    public JsonNode toJsonTree() throws NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

        node.put("elementID", this.id.toString());
        node.put("content", this.content);
        if(this.getParentElement() == null) {
            node.put("parentID", "null");
        } else {
            node.put("parentID", this.getParentElement().getId().toString());
        }
        if(!isChooseManualSummary() || summary == null || summary.getSummary() == null) {
            node.put("summary", "null");
        } else {
            node.put("summary", summary.toString());
        }
        node.put("chooseManualSummary", this.chooseManualSummary);
        arrayNode.add(node);
        return arrayNode;
    }

    /**
     * sets the given String as content
     * @param content to set
     */
    public void setContentManually(String content) {
        this.content = content;
    }

    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        StringBuilder text = map.get(key);

        if (this.chooseManualSummary && this.summary != null && this.summary.getSummary() != null && !this.summary.getSummary().isEmpty()) {
            this.summary.toLaTeX(map, key, level);
        }
        if (this.comment != null) {
            this.comment.toLaTeX(map, key, level);
        }
    }

    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        if(this.newLine != null) {
            this.newLine.toLaTeX(map, key, level);
        }
    }

    /**
     * checks whether the calling Element is a child of the passed Element
     * @param element to check
     * @return true if the calling Element is a child of the passed Element
     */
    public boolean checkOwnChild(Element element) {
        if(this.parentElement == null) {
            return false;
        }
        if(this.parentElement.equals(element)) {
            return true;
        }
        return this.parentElement.checkOwnChild(element);
    }
}
