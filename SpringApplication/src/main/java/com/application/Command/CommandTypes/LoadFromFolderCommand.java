package com.application.Command.CommandTypes;

import com.application.Exceptions.ParseException;
import com.application.Exceptions.ProcessingException;
import com.application.Interpreter.Parser;
import com.application.Printer.FilePrinter;
import com.application.Printer.Printer;
import com.application.Tree.elements.roots.Root;
import com.application.Tree.elements.roots.Roots;
import com.application.User;
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
                throw new ParseException("return value of parsing "+ root.getClass().getName());
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
