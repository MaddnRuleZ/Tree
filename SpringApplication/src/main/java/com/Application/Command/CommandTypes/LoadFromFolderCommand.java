package com.Application.Command.CommandTypes;

import com.Application.Exceptions.ParseException;
import com.Application.Exceptions.ProcessingException;
import com.Application.Interpreter.Parser;
import com.Application.Printer.FilePrinter;
import com.Application.Printer.Printer;
import com.Application.Tree.elements.roots.Root;
import com.Application.Tree.elements.roots.Roots;
import com.Application.User;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Command to load a tree from a folder
 */
public class LoadFromFolderCommand extends Command {
    /**
     *  user that holds information of LaTeX-Project
     */
    private User user;
    /**
     * path to the folder
     */
    private String path;

    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Printer printer = new FilePrinter(path, user.getRoot());

            Parser parser = new Parser(this.path);
            Roots root = parser.startParsing();
            if(root instanceof Root) {
                this.user.setRoot((Root) root);
                this.user.setPrinter(printer);
            } else {
                throw new ParseException("return value of parsing"+ root.getClass().getName());
            }
            this.setSuccess(true);

        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }
        return generateResponse(true);
    }


    public User getUser() {
        return user;
    }

    public String getPath() {
        return path;
    }


    public void setUser(User user) {
        this.user = user;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
