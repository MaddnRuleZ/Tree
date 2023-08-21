package com.application.tree.elements.roots;

import com.application.User;
import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.interfaces.JsonParser;
import com.application.tree.interfaces.LaTeXTranslator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This is the Singleton Function of the Root of the Whole LaTeX code.
 *
 *
 */
public class Root implements JsonParser, LaTeXTranslator, Roots {
    public static final String START_DOCUMENT = "\\begin{document}";
    public static final String END_DOCUMENT = "\\end{document}";
    private int minLevel;
    private final List<Element> childElements;
    private static Root instance;
    private List<String> startHeaderText;

    private Root() {
        // Private constructor to prevent instantiation from outside the class
        this.childElements = new ArrayList<>();
        this.minLevel = ElementConfig.BLOCK_ELEMENT_LEVEL;
    }

    /**
     * Singleton get Instance of current Root
     *
     * @return instance of Root Object
     */
    public static Root getInstance() {
        if (instance == null) {
            synchronized (Root.class) {
                if (instance == null) {
                    instance = new Root();
                }
            }
        }
        return instance;
    }

    /**
     * Reset the Singleton Root instance
     * Only called by {@link User} otherwise user will lose all data
     * @return new Root instance
     */
    public static Root resetInstance() {
        instance = null;
        return getInstance();
    }

    /**
     * Update the Minimum Level Cap from the Complete file
     *
     * @param newLevelCap level of new Created Element
     */
    public static void updateLevelCap(int newLevelCap) {
        if (newLevelCap < Root.getInstance().getMinLevel()) {
            Root.getInstance().setMinLevel(newLevelCap);
        }
    }

    public void addChild(Element element) {
        this.childElements.add(element);
    }

    /**
     * searches for the element with the given id
     *
     * @param id to search for
     * @return found Element or null
     */
    public Element searchForID(UUID id) {
        for (Element element: childElements) {
            Element foundElement = element.searchForID(id);
            if (foundElement != null) {
                return foundElement;
            }
        }
        return null;
    }

    /**
     * Returns the index of the child with the specified id
     * if no child with the specified id exists or the id is null, -1 is returned
     * @param id id of child to search for
     * @return index of child with specified id
     */
    public int getIndexOfChild(UUID id) {
        if(id == null) {
            return -1;
        }
        for (int i = 0; i < this.childElements.size(); i++) {
            if (this.childElements.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * calculates the level of the calling Element from bottom to top
     * should not be called
     * @return level of the calling Element
     */
    public int calculateLevelFromElement() {
        assert false;
        return -1;
    }

    /**
     * add the startHeader e.g. the text before \begin{document} to the Root
     *
     * @param startHeaderText List of Strings that represent the StartHeader
     */
    public void addStartHeader(List<String> startHeaderText) {
        this.startHeaderText = startHeaderText;
    }
    public boolean startHeaderExists() {
        return this.startHeaderText == null;
    }

    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        if (!this.childElements.isEmpty()) {
            ArrayNode childrenNode = JsonNodeFactory.instance.arrayNode();
            for (Element child : this.childElements) {
                childrenNode.add(child.toJsonEditor());
            }
            node.set("editor", childrenNode);
        }
        return node;
    }

    @Override
    public JsonNode toJsonTree() throws NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode treeNode = mapper.createObjectNode();
        ArrayNode node = JsonNodeFactory.instance.arrayNode();
        if (!this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                node.addAll((ArrayNode) child.toJsonTree());
            }
        }
        treeNode.set("tree", node);
        return treeNode;
    }

    @Override
    public void toLaTeX(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        toLaTeXStart(map, key, level, exportComment, exportSummary);

        if (!this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key, level + 1, exportComment, exportSummary);
            }
        }
        toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }

    /**
     * adds the startHeader and the text before \begin{document}
     */
    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        StringBuilder text = map.get(key);
        if(this.startHeaderText != null) {
            for(String line : this.startHeaderText){
                text.append(line);
                text.append("\n");
            }
        }
    }

    /**
     * adds the endHeader
     */
    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append(END_DOCUMENT);
    }

    /**
     * adds a child on the given index
     * @param index index to add the child
     * @param newChild child to add
     */
    public void addChildOnIndex(int index, Element newChild) {
        this.childElements.add(index, newChild);
    }

    public void removeChild(Element element) {
        this.childElements.remove(element);
    }

    public List<Element> getChildren() {
        return this.childElements;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }
}
