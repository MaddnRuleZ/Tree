package com.application.command.types;

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
     * path to the folder
     */
    private String path;

    @Override
    public JsonNode execute() {
        this.getLockManager().acquireStructureWriteLock();
        try {
            Printer printer = new FilePrinter(path, this.getUser());
            this.setSuccess(load(this.getUser(), printer, path));
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            this.getLockManager().releaseStructureWriteLock();
        }
        return generateResponse(true);
    }

    @JsonProperty
    public String getPath() {
        return path;
    }


    @JsonProperty
    public void setPath(String path) {
        this.path = path;
    }


}
