package com.Application.Tree.elements.roots;

import com.Application.Exceptions.UnknownElementException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.interfaces.LaTeXTranslator;
import com.Application.Tree.interfaces.JsonParser;
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
 */

public class Root implements JsonParser, LaTeXTranslator, Roots {
    public static int MIN_LEVEL = ElementConfig.MAX_LEVEL;
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
     * Update the Minimum Level Cap from the Complete file
     *
     * @param newLevelCap level of new Created Element
     */
    public static void updateLevelCap(int newLevelCap) {
        if (newLevelCap < MIN_LEVEL) {
            MIN_LEVEL = newLevelCap;
        }
    }

    /**
     * Check if the Root is already initialized
     *
     * @return true if Root is init, else false
     */
    public static boolean isInit() {
        return instance == null;
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
     * add the startHeader e.g the text before \startDocument to the Root
     *
     * @param startHeaderText
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
    public void toLaTeX(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        toLaTeXStart(map, key);
        StringBuilder text = map.get(key);
        if (this.childElements != null && !this.childElements.isEmpty()) {
            for (Element child : this.childElements) {
                child.toLaTeX(map, key);
            }
        }
        toLaTeXEnd(map, key);
    }

    /**
     * adds the startHeader e.g the text before \startDocument to the Root
     * @param map of the LaTeX Code
     * @param key of the LaTeX Code
     * @throws UnknownElementException
     */
    @Override
    public void toLaTeXStart(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append(this.startHeaderText);
        text.append("\n");
    }

    /**
     * adds the endHeader e.g the text after \endDocument to the Root
     * @param map of the LaTeX Code
     * @param key of the LaTeX Code
     * @throws UnknownElementException
     */
    @Override
    public void toLaTeXEnd(Map<String, StringBuilder> map, String key) throws UnknownElementException {
        StringBuilder text = map.get(key);
        text.append("\\end{document}");
    }

    public List<Element> getChildren() {
        return this.childElements;
    }

}
