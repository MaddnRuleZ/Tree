package com.application.command.types.interfaces;

import com.application.User;
import com.application.exceptions.FileInvalidException;
import com.application.exceptions.ParseException;
import com.application.interpreter.Parser;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;

public interface ILoadCommand {

    default boolean load(User user, Printer printer, String path) throws ParseException, FileInvalidException {
        Parser parser = new Parser(path);
        Roots root = parser.startParsing();
        if(root instanceof Root) {
            user.setRoot((Root) root);
            user.setPrinter(printer);
        } else {
            throw new ParseException(this.getClass().getSimpleName(), root.getClass().getSimpleName(), Root.class.getSimpleName());
        }
        return true;
    }

}
