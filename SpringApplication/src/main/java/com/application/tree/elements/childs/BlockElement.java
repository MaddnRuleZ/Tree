package com.application.tree.elements.childs;

import com.application.exceptions.UnknownElementException;
import com.application.interpreter.TextFileReader;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.elements.parent.Figure;

import java.util.Map;

/**
 * BlockElement Class
 *
 * The BlockElement represents a child element within the LaTeX Composite Tree.
 * BlockElements are undetected Blocks of Text which represent no detected Structure Element and
 * still hold the Text in Between Structure Elements
 */
public class BlockElement extends Child {

    /**
     * Creates a new instance of BlockElement being a Child with default values.
     */
    public BlockElement() {
        super(null, null, ElementConfig.BLOCK_ELEMENT_LEVEL);
        this.type = this.getClass().getSuperclass().getSimpleName();
    }

    /*
     * Add a new TextBlock or add Text to the Block
     * if there is a newLineCharacter generate a new TextBlock on the same level
     *
     * @param line line to Scan for Summary Comment or NewLine
     *
    public Element addTextBlockToElem1(String line) {
        if (newLine.checkLineForNewLineCharacters(line)) {
            newLine.extractNlChar(line);
            return generateTextBlockSameLevel();
        } else {
            if (!summary.extractSummary(line) && !comment.extractComment(line)) {
                if (parentElement instanceof Figure figure) {
                    if (!figure.setGraphics(line) && !figure.addCaption(line)) {
                        addText(line);
                    }
                } else {
                    addText(line);
                }
            }
            return this;
        }
    }
    */

    /**
     * Add a new TextBlock or add Text to the Block
     * if there is a newLineCharacter generate a new TextBlock on the same level
     * extract summary and comment if present,
     * and check for graphic and caption command if current TextBlock is inside a Figure
     *
     * @param line line to Scan for Summary Comment or NewLine
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (newLine.checkLineForNewLineCharacters(line)) {
            newLine.extractNlChar(line);
            return generateTextBlockSameLevel();
        }

        if (summary.extractSummary(line) || comment.extractComment(line)) {
            return this;
        }

        if (parentElement instanceof Figure figure) {
            if (figure.setGraphics(line) || figure.addCaption(line)) {
                return this;
            }
        }

        addText(line);
        return this;
    }




    /**
     * Add text as Content to the BlockElement,
     * remove all leading space characters and add a new line to each line
     *
     * @param line text to add to the BlockElement
     */
    private void addText(String line) {
        this.textBuilder.append(TextFileReader.removeSpacesFromStart(line)).append("\n");
        this.content = textBuilder.toString();
    }

    @Override
    public void toLaTeX(Map<String,StringBuilder> map, String key, int level, boolean exportComment, boolean exportSummary) throws UnknownElementException {
        super.toLaTeXStart(map, key, level, exportComment, exportSummary);
        String indentation = getIndentation(level);
        StringBuilder text = map.get(key);

        if(this.content != null){
            for(String line : this.content.split("\n")){
                text.append(indentation).append(line).append("\n");
            }
        }
        super.toLaTeXEnd(map, key, level, exportComment, exportSummary);
    }
}
