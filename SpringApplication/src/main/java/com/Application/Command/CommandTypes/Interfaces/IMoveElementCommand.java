package com.Application.Command.CommandTypes.Interfaces;

import com.Application.Exceptions.ElementNotFoundException;
import com.Application.Exceptions.LevelException;
import com.Application.Exceptions.OwnChildException;
import com.Application.Tree.Element;
import com.Application.Tree.elements.ElementConfig;
import com.Application.Tree.elements.parent.Parent;

import java.util.UUID;

/**
 * Interface that moves an element in the treeStructure
 */
public interface IMoveElementCommand {
    /**
     * moves an element in the treeStructure
     */
    default void moveElement(Element element, Parent newParent, UUID previousElement, int minLevel) throws OwnChildException, LevelException , ElementNotFoundException {
      if(checkPossibleLevel(element, newParent, minLevel)){
            if(checkPossibleParent(element, newParent)) {
                Parent oldParent = element.getParentElement();
                element.setParent(newParent);
                int indexPreviousElement = newParent.getIndexOfChild(previousElement);
                newParent.addChildOnIndex(indexPreviousElement, element);
                oldParent.removeChild(element);
            } else {
                throw new OwnChildException();
            }
        } else {
            throw new LevelException();
        }
    }

    //TODO minLevel, maxLevel ???

    /**
     * checks if it is possible to move an element to a new parent
     * check if new level of deepest sectioning child lies within part-level and subParagraph-level
     *
     * @param element   element to move
     * @param newParent new parent of the element
     * @param minLevel  minimum level of the element
     * @return true if it is possible to move the element, false otherwise
     */
    default boolean checkPossibleLevel(Element element, Parent newParent, int minLevel) {
        if (element instanceof Parent) {
            int newLevel = newParent.calculateLevelFromElement();
            int deepestSectioningChild = element.levelOfDeepestSectioningChild();
            return minLevel + newLevel + deepestSectioningChild + ElementConfig.PART.getLevel() <= ElementConfig.SUBPARAGRAPH.getLevel();
        }
        return true;
    }

    default boolean checkPossibleParent(Element element, Parent newParent) {
        if (element instanceof Parent) {
            return !newParent.checkOwnChild(element);
        }
        return true;
    }
}
