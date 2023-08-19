package com.application.command.types.interfaces;

import com.application.exceptions.ElementNotFoundException;
import com.application.exceptions.LevelException;
import com.application.exceptions.OwnChildException;
import com.application.tree.Element;
import com.application.tree.elements.ElementConfig;
import com.application.tree.elements.parent.Parent;
import com.application.tree.elements.roots.Root;

import java.util.UUID;

/**
 * Interface that moves an element in the treeStructure
 */
public interface IMoveElementCommand {
    /**
     * moves an element in the treeStructure
     */
    default void moveElement(Element element, Parent newParent, UUID previousElement, int minLevel) throws OwnChildException, LevelException, ElementNotFoundException {
        int indexPreviousElement = Root.getInstance().getIndexOfChild(previousElement);
        if(indexPreviousElement == -2) {
            throw new ElementNotFoundException("PreviousElement");
        }

        if(newParent == null) {
            Parent oldParent = element.getParentElement();
            element.setParent(null);
            if(oldParent == null) {
                Root.getInstance().removeChild(element);
            } else {
                oldParent.removeChild(element);
            }
            Root.getInstance().addChildOnIndex(indexPreviousElement + 1, element);

            return;
        }

        if(checkPossibleLevel(element, newParent, minLevel)){
            if(checkPossibleParent(element, newParent)) {
                Parent oldParent = element.getParentElement();
                if(oldParent == null) {
                    Root.getInstance().removeChild(element);
                } else {
                    oldParent.removeChild(element);
                }
                element.setParent(newParent);
                newParent.addChildOnIndex(indexPreviousElement + 1, element);
            } else {
                throw new OwnChildException();
            }
        } else {
            throw new LevelException();
        }
    }

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
