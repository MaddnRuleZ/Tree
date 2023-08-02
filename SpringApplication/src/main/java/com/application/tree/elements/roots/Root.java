package com.application.tree.elements.roots;

import com.application.exceptions.UnknownElementException;
import com.application.tree.Element;
import com.application.tree.interfaces.LaTeXTranslator;
import com.application.tree.interfaces.JsonParser;
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
    public static int MIN_LEVEL;
    private final List<Element> childElements;
    private static Root instance;
    private List<String> startHeaderText;

    private Root() {
        // Private constructor to prevent instantiation from outside the class
        childElements = new ArrayList<>();
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
     * Reset the Singleton Root instance.
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
        if (newLevelCap < MIN_LEVEL) {
            MIN_LEVEL = newLevelCap;
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
     * calculates the level of the calling Element from bottom to top
     * @return level of the calling Element
     */
    public int calculateLevelFromElement() {
        return -1;
    }

    /**
     * add the startHeader e.g the text before \begin{document} to the Root
     *
     * @param startHeaderText List of Strings that represent the StartHeader
     */
    public void addStartHeader(List<String> startHeaderText) {
        this.startHeaderText = startHeaderText;
    }

    @Override
    public ObjectNode toJsonEditor() throws NullPointerException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        if (this.childElements != null && !this.childElements.isEmpty()) {
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
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                node.addAll((ArrayNode) child.toJsonTree());
            }
        }
        treeNode.set("tree", node);
        return treeNode;
    }

    @Override
    public void toLaTeX(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        toLaTeXStart(map, key, level);
        StringBuilder text = map.get(key);
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key, level);
            }
        }
        toLaTeXEnd(map, key, level);
    }

    /**
     * adds the startHeader and the text before \begin{document}
     *
     * @param map   of the LaTeX Code
     * @param key   of the LaTeX Code
     * @param level
     * @throws UnknownElementException
     */
    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        StringBuilder text = map.get(key);

        for(String line : this.startHeaderText){
            text.append(line);
            text.append("\n");
        }
    }

    /**
     * adds the endHeader
     *
     * @param map   of the LaTeX Code
     * @param key   of the LaTeX Code
     * @param level
     * @throws UnknownElementException
     */
    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key, int level) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append(END_DOCUMENT);
    }

    public List<Element> getChildren() {
        return this.childElements;
    }
}
