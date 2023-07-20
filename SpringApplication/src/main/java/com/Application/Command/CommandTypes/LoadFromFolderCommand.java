package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Interpreter.Parser;
import com.Application.Printer.FilePrinter;
import com.Application.Tree.elements.Root;
import com.Application.Tree.interfaces.Roots;
import com.Application.User;
import com.fasterxml.jackson.core.JsonProcessingException;
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
            Parser parser = new Parser(this.path);
            Roots root = parser.startParsing();
            if(root instanceof Root) {
                this.user.setRoot((Root) root);
                this.user.setPrinter(new FilePrinter(this.path, this.user.getRoot()));
            } else {
                this.setFailureMessage("Something went wrong while parsing the folder");
                this.setSuccess(false);
            }
            this.setSuccess(true);
        } catch (Exception e) {
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
