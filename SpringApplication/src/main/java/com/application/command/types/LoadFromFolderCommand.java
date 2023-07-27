package com.application.command.types;

import com.application.exceptions.ParseException;
import com.application.exceptions.ProcessingException;
import com.application.interpreter.Parser;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * command to load a tree from a folder
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
