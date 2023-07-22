package com.Application.Command.CommandTypes.Interfaces;

import com.Application.Exceptions.ElementNotFoundException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.elements.parent.Parent;

/**
 * Interface that moves an element in the treeStructure
 */
public interface IMoveElementCommand {
    /**
     * moves an element in the treeStructure
     */
    default void moveElement(Element element, Parent newParent, Element previousElement, int minLevel) throws IndexOutOfBoundsException, ElementNotFoundException {
        if (checkPossible(element, newParent, minLevel)) {
            Parent oldParent = element.getParentElement();
            element.setParent(newParent);
            newParent.addChildAfter(previousElement, element);
            oldParent.removeChild(element);
        } else {
            throw new IndexOutOfBoundsException("Sectioning Elemente dürfen nur bis zu einem Level von " + ElementConfig.SUBPARAGRAPH.getLevel() + " verschachtelt werden.");
        }
    }

    //TODO minLevel, maxLevel ???
    /**
     * checks if it is possible to move an element to a new parent
     * check if new level of deepest sectioning child lies within part-level and subParagraph-level
     * @param element element to move
     * @param newParent new parent of the element
     * @param minLevel minimum level of the element
     * @return true if it is possible to move the element, false otherwise
     */
    default boolean checkPossible(Element element, Parent newParent, int minLevel) {
        if(element instanceof Parent) {
            int newLevel = newParent.calculateLevelFromElement();
            int deepestSectioningChild = element.levelOfDeepestSectioningChild();
            return minLevel + newLevel + deepestSectioningChild + ElementConfig.PART.getLevel() <= ElementConfig.SUBPARAGRAPH.getLevel();
        }
        return true;
    }

}
