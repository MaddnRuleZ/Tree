package com.application.tree;

import java.util.UUID;

/**
 * provides functionality to execute Commands
 */

public interface IElement {
    /**
     * calculates the level of the calling Element from bottom to top
     * @return level of the calling Element
     */
    int calculateLevelFromElement();

    /**
     * searches for the element with the given id
     *
     * @param id to search for
     * @return found Element or null
     */
    Element searchForID(UUID id);

    /**
     * traverses the tree starting from calling Element
     * Passed Sections add 1 to the level
     *
     * @return level of the deepest sectioning child
     */
    int levelOfDeepestSectioningChild();

    /**
     * checks if the calling Element is a child of the passed Element
     * @param element to check
     * @return true if the calling Element is a child of the passed Element
     */
    boolean checkOwnChild(Element element);
}
