package com.application.tree.elements.parent;

import com.application.exceptions.ImageException;
import com.application.exceptions.ProcessingException;
import com.application.exceptions.UnknownElementException;
import com.application.printer.Printer;
import com.application.tree.Element;
import com.application.tree.elements.childs.BlockElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

/**
 * Figure Class
 *
 * The Figure class represents an environment that contains graphical elements with captions in the Parser.
 *
 * The Figure environment typically includes graphics identified by "\\includegraphics" and their corresponding
 * captions identified by "\\caption". The "graphic" field stores the "\\includegraphics" content for this figure.
 *
 * The captions are stored in the "captions" list, which can contain multiple captions associated with the figure.
 */
public class Figure extends Environment {
    public static final String CAPTION_IDENTIFIER = "\\caption";
    public static final String GRAPHICS_IDENTIFIER = "\\includegraphics";
    private final List<String> captions;
    private String graphicOptions = "";
    private String graphic;

    /**
     * Constructor for creating a new Figure object with the specified startPart, endPart, and level.
     *
     * @param startPart The startPart string that identifies the beginning of the figure environment.
     * @param endPart   The endPart string that identifies the end of the figure environment.
     * @param level     The hierarchical level of the figure environment.
     */
    public Figure(String startPart, String endPart, int level) {
        super(startPart, startPart, endPart, level);
        captions = new ArrayList<>();
    }

    /**
     * Add a new TextBlock to the Figure.
     * If there are already child elements in the Environment, the new TextBlock will be added at the same level.
     * Otherwise, it will be added as a child of the Environment.
     *
     * @param line The line to scan for Summary Comment or NewLine to be added to the TextBlock.
     * @return The newly created or existing TextBlockElement where the line is added.
     */
    @Override
    public Element addTextBlockToElem(String line) {
        BlockElement block;
        if (this.children.size() == 0) {
            block = generateTextBlockAsChild();
        } else {
            block = generateTextBlockSameLevel();
        }
        block.addTextBlockToElem(line);
        return block;
    }

    /**
     * if the String Contains a Part of the Graphic-string extract it,
     * extract both the options part and the content part
     *
     * @param graphicsString raw graphic string to parse
     * @return true upon success, false otherwise
     */
    public boolean setGraphics(String graphicsString) {
        if (!graphicsString.contains(GRAPHICS_IDENTIFIER)) {
            return false;
        }

        graphic = extractContent(graphicsString);
        graphicOptions = extractOptions(graphicsString);
        return graphic != null && graphicOptions != null;
    }

    /**
     * check if the line is a caption, if so add it to the Figure and return true
     *
     * @param caption current Line to check for Caption
     * @return true if caption was added, otherwise false
     */
    public boolean addCaption(String caption) {
        if (!caption.contains(CAPTION_IDENTIFIER)) {
            return false;
        }
        return captions.add(extractContent(caption));
    }


    public void toLaTeX(Map<String, StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXStart(map, key, level, exportComment, exportSummary);
        String indentationBody = getIndentation(level+1);

        StringBuilder text = map.get(key);

        // \caption{caption_content}
        for (String caption : captions) {
            text.append(indentationBody).append(CAPTION_IDENTIFIER).append("{").append(caption).append("}");
            text.append("\n");
        }

        // \includegraphics{graphic}
        text.append(indentationBody).append(GRAPHICS_IDENTIFIER).append(this.graphicOptions).append("{").append(this.graphic).append("}");
        text.append("\n");

        // children
        if (this.children != null && !this.children.isEmpty()) {
            for (Element child : this.children) {
                child.toLaTeX(map, key, level + 1, exportComment, exportSummary );
            }
        }

        super.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }


    @Override
    public ObjectNode toJsonEditor() throws NullPointerException, ProcessingException, IOException {
        ObjectNode node = super.toJsonEditor();
        node.put("image", convertFile());
        node.put("mimeType", extractMimeType());

        if(this.captions != null && !this.captions.isEmpty()) {
            ArrayNode captionNode = JsonNodeFactory.instance.arrayNode();
            for(String caption : this.captions) {
                node.put("content", caption);
            }
            node.set("captions", captionNode);
        } else {
            node.set("captions", null);
        }
        return node;
    }

    @Override
    public JsonNode toJsonTree() throws NullPointerException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();

        node.put("elementID", this.getId().toString());
        node.put("content", "");

        node.put("image", convertFile());
        node.put("mimeType", extractMimeType());

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
        node.put("chooseManualSummary", this.isChooseManualSummary());
        arrayNode.add(node);
        return arrayNode;
    }

    private String convertFile() throws IOException {
        try {
            Path location = Paths.get(Printer.getFigurePath() + "/" + this.graphic);

            Resource resource = new UrlResource(location.toUri());

            byte[] imageBytes = resource.getInputStream().readAllBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            return base64Image;
        } catch (IOException | NullPointerException e) {
            Path location = Paths.get("src/main/resources/Images/ImageNotFound.txt");
            return Files.readString(location);
        }
    }

    private String extractMimeType() {
        try {
            return Files.probeContentType(Path.of(this.graphic));
        } catch (IOException | NullPointerException e) {
            return "image/jpg";
        }
    }
}
