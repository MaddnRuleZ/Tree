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
 * The BlockElement class represents a child element within the LaTeX Code.
 * BlockElements are undetected Blocks of Text which represent no detected Structure Element
 */
public class BlockElement extends Child {

    /**
     * Call the constructor of the parent class (Child) with null startPart and endPart and the block element level.
     */
    public BlockElement() {
        super(null, null, ElementConfig.BLOCK_ELEMENT_LEVEL);
        this.type = this.getClass().getSuperclass().getSimpleName();
    }

    /**
     * add a TextBlock to the Element and generate it on the same Level
     * Check if Parent is Figure -> this BlockElement inside Figure
     * add Caption / GraphicString to the Figure when in current Line
     *
     * @param line line to Scan for Summary Comment or NewLine
     */
    @Override
    public Element addTextBlockToElem(String line) {
        if (newLine.checkLineForNewLineCharacters(line)) {
            newLine.extractContent(line);
            return generateTextSameLevel();
        } else {
            if (!summary.extractContent(line) && !comment.extractContent(line)) {
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

    /**
     * Add text as Content to the BlockElement,
     * remove all leading space characters
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
