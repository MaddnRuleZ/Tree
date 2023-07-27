package com.application.command.types;

import com.application.User;
import com.application.command.types.interfaces.ILoadCommand;
import com.application.exceptions.ProcessingException;
import com.application.printer.FilePrinter;
import com.application.printer.Printer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * command to load a tree from a folder
 */
public class LoadFromFolderCommand extends Command implements ILoadCommand {
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
            this.setSuccess(load(user, printer, path));
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }
        return generateResponse(true);
    }


    @JsonProperty
    public User getUser() {
        return user;
    }

    @JsonProperty
    public String getPath() {
        return path;
    }



    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public void setPath(String path) {
        this.path = path;
    }


}
