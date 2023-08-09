package com.application.command.types.interfaces;

import com.application.exceptions.ParseException;
import com.application.interpreter.Parser;
import com.application.tree.elements.childs.BlockElement;
import com.application.tree.elements.roots.Roots;

public interface IEditCommand {

    default void checkBadString(String content) throws ParseException {
        Parser parser = new Parser();
        Roots root = parser.startParsing(content);
        if(root.getChildren().size() == 0) {
            return;
        }

        if(root.getChildren().size() != 1 || root.getChildren().get(0).getClass() != BlockElement.class) {
            throw new ParseException();
        }
    }
}
