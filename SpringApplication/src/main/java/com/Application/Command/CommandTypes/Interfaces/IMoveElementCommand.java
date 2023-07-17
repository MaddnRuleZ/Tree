package com.Application.Command.CommandTypes.Interfaces;

import com.Application.Tree.Element;
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
        if (checkPossible(element, newParent, 0, 10)) {
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
     * @param element element to move
     * @param newParent new parent of the element
     * @param minLevel level at which the counting starts
     * @param maxLevel maximum level of the treeStructure
     * @return true if it is possible to move the element, false otherwise
     */
    default boolean checkPossible(Element element, Parent newParent, int minLevel, int maxLevel) {
        if(element instanceof Parent) {
            int newLevel = newParent.calculateLevelFromElement();
            int deepestSectioningChild = element.levelOfDeepestSectioningChild();
            if (newLevel + deepestSectioningChild + minLevel > maxLevel) {
                return false;
            }
        }
        return true;
    }

}
