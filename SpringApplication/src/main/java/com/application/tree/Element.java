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
 * Element representing a LaTeX Command
 *
 * This class serves as the base abstract class for different LaTeX elements
 * that can be part of a document structure. It contains common functionality
 * and attributes that are shared among its subclasses.
 */
public abstract class Element implements JsonParser, LaTeXTranslator, IElement {
    protected static final String CONTENT_REGEX = "\\{([^}]+)\\}";
    private static final String OPTIONS_REGEX = "\\[([^\\]]+)\\]";
    private UUID id;
    private final int level;
    protected Parent parentElement;
    protected StringBuilder textBuilder;
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
     * Create a new Structure Element
     * representing a LaTeX Command for Sectioning different Parts in a Document
     *
     * @param startPart startString to initialize the Element
     * @param endPart endingString to initialize the Element, can be null
     * @param level SEMI Order Level for sorting by hierarchy (Check ElementConfig for detail)
     */
    public Element(String startPart, String endPart, int level) {
        this.id = UUID.randomUUID();
        this.startPart = startPart;
        this.endPart = endPart;
        this.level = level;
        this.textBuilder = new StringBuilder();

        comment = new Comment();
        summary = new Summary();
        newLine = new NewLine();
        Root.updateLevelCap(level);
    }

    /**
     * Add an TextBlock to the currentElement based on the currentText Line
     *
     * @param line current Line in the Text
     * @return the new Created TextBlockElement
     */
    public abstract Element addTextBlockToElem(String line);

    /**
     * generate an TextBlock on the Same Level the current Element is on
     * add the parent of this Element a TextBlock and set the TextBlocks parent as the parent
     *
     * @return return the BlockElement just Created
     */
    public BlockElement generateTextSameLevel() {
        BlockElement blockElement = new BlockElement();
        parentElement.addChild(blockElement);
        blockElement.setParent(parentElement);
        return blockElement;
    }

    public void setChooseManualSummary(boolean chooseManualSummary) {
        this.chooseManualSummary = chooseManualSummary;
    }

    /**
     * Extract and set the Options String of the Element
     *
     * @param options whole String to extract Options from
     */
    public void setOptions(String options) {
        Pattern pattern = Pattern.compile(OPTIONS_REGEX);
        Matcher matcher = pattern.matcher(options);

        if (matcher.find()) {
            this.options = matcher.group(1);
        } else {
            this.options = null;
        }
    }

    /**
     * Extract and set the Content String of the Element
     *
     * @param content whole String to extract Content from
     */
    public void setContent(String content) {
        Pattern pattern = Pattern.compile(CONTENT_REGEX);
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
     *
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
     *
     * @param summary
     */
    public void setSummary(String summary) {
       this.summary.setSummary(summary);
    }

    @Override
    public int calculateLevelFromElement() {
        Parent parent = this.getParentElement();
        if (parent == null) {
            return 0;
        }
        return parent.calculateLevelFromElement();
    }

    @Override
    public boolean checkOwnChild(Element element) {
        if(this.parentElement == null) {
            return false;
        }
        if(this.parentElement.equals(element)) {
            return true;
        }
        return this.parentElement.checkOwnChild(element);
    }

    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("id", this.id.toString());
        node.put("type", this.type);

        if (this.getParentElement() == null) {
            node.put("parent", "null");
        } else {
            node.put("parent", this.getParentElement().getId().toString());
        }

        node.put("content", this.content);
        node.put("comment", comment.toString());
        if(!hasSummary()) {
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
        if (this.getParentElement() == null) {
            node.put("parentID", "null");
        } else {
            node.put("parentID", this.getParentElement().getId().toString());
        }
        if(!hasSummary()) {
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

        if (hasSummary()) {
            this.summary.toLaTeX(map, key, level);
        }
        if (hasComment()) {
            this.comment.toLaTeX(map, key, level);
        }
    }

    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        if(hasNewLine()) {
            this.newLine.toLaTeX(map, key, level);
        }
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

    /**
     * @return true, if the Element has a Summary and chooses the manual Summary
     */
    private boolean hasSummary() {
        return this.chooseManualSummary && !(this.summary == null)  && !this.summary.getSummary().isEmpty();
    }

    private boolean hasComment() {
        return this.comment != null && !this.comment.getComments().isEmpty();
    }

    private boolean hasNewLine() {
        return this.newLine != null && this.newLine.getNewLine() != null;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public Summary getSummary() {
        return summary;
    }

    public Comment getComment() {
        return comment;
    }

    public NewLine getNewLine() {
        return newLine;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
