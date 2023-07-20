package com.Application.Command.CommandTypes.Interfaces;

import com.Application.Tree.Element;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.elements.Parent;

/**
 * Interface that moves an element in the treeStructure
 */
public interface IMoveElementCommand {
    /**
     * moves an element in the treeStructure
     * @return true if the element was moved successfully, false otherwise
     */
    default boolean moveElement(Element element, Parent newParent, Element previousElement) throws IndexOutOfBoundsException, NullPointerException{
        if (checkPossible(element, newParent)) {
            Parent oldParent = element.getParentElement();
            element.setParent(newParent);
            newParent.addChildAfter(previousElement, element);
            oldParent.removeChild(element);
            return true;
        }
        return false;
    }

    //TODO minLevel, maxLevel ???
    /**
     * checks if it is possible to move an element to a new parent
     * check if new level of deepest sectioning child lies within part-level and subParagraph-level
     * @param element element to move
     * @param newParent new parent of the element
     * @return true if it is possible to move the element, false otherwise
     */
    default boolean checkPossible(Element element, Parent newParent) {
        if(element instanceof Parent) {
            int newLevel = newParent.calculateLevelFromElement();
            int deepestSectioningChild = element.levelOfDeepestSectioningChild();
            if (newLevel + deepestSectioningChild + ElementConfig.PART.getLevel() > ElementConfig.SUBPARAGRAPH.getLevel()) {
                return false;
            }
        }
        return true;
    }

}
