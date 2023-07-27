package com.application.Command.CommandTypes.Interfaces;

import com.application.Exceptions.LevelException;
import com.application.Exceptions.OwnChildException;
import com.application.Tree.Element;
import com.application.Tree.elements.ElementConfig;
import com.application.Tree.elements.parent.Parent;

import java.util.UUID;

/**
 * Interface that moves an element in the treeStructure
 */
public interface IMoveElementCommand {
    /**
     * moves an element in the treeStructure
     */
    default void moveElement(Element element, Parent newParent, UUID previousElement, int minLevel) throws OwnChildException, LevelException {
      if(checkPossibleLevel(element, newParent, minLevel)){
            if(checkPossibleParent(element, newParent)) {
                Parent oldParent = element.getParentElement();
                element.setParent(newParent);
                int indexPreviousElement = newParent.getIndexOfChild(previousElement);
                newParent.addChildOnIndex(indexPreviousElement + 1, element);
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
