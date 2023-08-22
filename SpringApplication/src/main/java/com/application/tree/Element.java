package com.application.tree;

import com.application.exceptions.UnknownElementException;
import com.application.tree.additionalInfo.Comment;
import com.application.tree.additionalInfo.NewLine;
import com.application.tree.additionalInfo.Summary;
import com.application.tree.elements.childs.BlockElement;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;
import com.application.tree.interfaces.JsonParser;
import com.application.tree.interfaces.LaTeXTranslator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Element representing a LaTeX Command starting with the character "\"
 *
 * This class serves as the base abstract class for different LaTeX elements
 * that can be part of a document structure. It contains common functionality
 * and attributes that are shared among its subclasses.
 */
public abstract class Element implements JsonParser, LaTeXTranslator, IElement {
    /**
     * Elements in curly Brackets : {<any>}
     */
    public static final String CONTENT_REGEX = "\\{(.*?)\\}";
    /**
     * Elements in Brackets : [<any>]
     */
    public static final String OPTIONS_REGEX = "\\[(.*?)\\]";
    /**
     * '*' check for * for uncounted sectioning types
     */
    private static final String UNCOUNTED_REGEX = ".*\\*\\{.*";
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
     * set the new TextBlock's parent equal to this Elements parent
     * set this as the child of the parent
     *
     * @return return the BlockElement just Created
     */
    public BlockElement generateTextBlockSameLevel() {
        BlockElement blockElement = new BlockElement();
        blockElement.setParent(parentElement);

        if (parentElement == null) {
            Root.getInstance().addChild(blockElement);
        } else {
            parentElement.addChild(blockElement);
        }

        return blockElement;
    }

    public void setChooseManualSummary(boolean chooseManualSummary) {
        this.chooseManualSummary = chooseManualSummary;
    }

    /**
     * Extract and set the Options String of the Element,
     * option string can only be an '*' for an uncounted LaTeX element
     *
     * @param options whole String to extract Options from
     */
    public void setOptions(String options) {
        this.options = extractOptions(options);
        if (this.options == null && Pattern.matches(UNCOUNTED_REGEX, options)) {
            this.options = "*";
        }
    }


    /**
     * Extracts content from a raw line using regex
     *
     * @param rawLine Input raw line.
     * @return Extracted content, or null if no match is found.
     */
    public String extractContent(String rawLine) {
        Matcher matcher = Pattern.compile(CONTENT_REGEX).matcher(rawLine);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * Extracts Options from a raw line using regex
     *
     * @param rawLine Input raw line.
     * @return Extracted content, or null if no match is found.
     */
    protected String extractOptions(String rawLine) {
        Matcher matcher = Pattern.compile(OPTIONS_REGEX).matcher(rawLine);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public void setContent(String rawLine) {
        this.content = extractContent(rawLine);
    }

    /**
     * parses the incoming String into comment
     * sets the comment of the calling Element
     *
     * @param comment to parse
     */
    public void setComment(String comment) {
        this.comment.getComments().clear();
        for (String line: comment.split(Comment.COMMENT_START_CHARACTER)) {
            if(!line.isBlank()) {
                this.comment.addComment(line);
            }
        }
    }

    /**
     * parses the incoming String into summary
     * @param summary to parse
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
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        if (hasComment()) {
            this.comment.toLaTeX(map, key, level, exportComment, exportSummary);
        }
    }

    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        if (hasSummary()) {
            this.summary.toLaTeX(map, key, level, exportComment, exportSummary);
        }

        if(hasNewLine()) {
            this.newLine.toLaTeX(map, key, level, exportComment, exportSummary);
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
    protected boolean hasSummary() {
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

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }
}
