package com.Application.Tree;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.additionalInfo.Comment;
import com.Application.Tree.additionalInfo.NewLine;
import com.Application.Tree.additionalInfo.Summary;
import com.Application.Tree.elements.Parent;
import com.Application.Tree.elements.Root;
import com.Application.Tree.interfaces.LaTeXTranslator;
import com.Application.Tree.interfaces.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
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

    public abstract Element addTextBlockToElem(String line);

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
    public String toJson() {
        return null;
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
        Pattern pattern = Pattern.compile("\\[([^]]*)\\]");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            this.content = matcher.group(1);
        } else {
            this.content = null;
        }
    }

    /**
     * parses the incoming String into comment
     * @param comment
     */
    public void setComment(String comment) {
        //TODO
    }

    public void setSummary(String summary) {
        //TODO
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



    //TODO summary
    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        node.put("id", this.id.toString());
        node.put("type", this.getClass().getSimpleName());

        if(this.getParentElement() == null) {
            node.put("parent", "null");
        } else {
            node.put("parent", this.getParentElement().getId().toString());
        }
        node.put("content", this.content);
        node.put("comment", comment.toString());
        node.put("summary", "summary");
        node.put("chooseManualSummary", this.chooseManualSummary);

        return node;
    }

    //TODO summary
    @Override
    public ArrayNode toJsonTree() throws NullPointerException {
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
        node.put("summary", "summary");

        arrayNode.add(node);
        return arrayNode;
    }

    /**
     * sets the given String as content
     * @param content
     */
    public void setContentManually(String content) {
        this.content = content;
    }
}
