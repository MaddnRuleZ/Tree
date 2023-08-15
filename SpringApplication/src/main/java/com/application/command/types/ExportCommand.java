package com.application.command.types;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * responsible for overwriting the LaTeX file with changes
 * that have been made in the tree structure
 */
public class ExportCommand extends Command {

    private boolean exportSummary = true;

    private boolean exportComment = true;


    @Override
    public JsonNode execute() {
        this.getLockManager().acquireStructureReadLock();
        try {
            this.getUser().getPrinter().setExportComments(this.exportComment);
            this.getUser().getPrinter().setExportSummary(this.exportSummary);
            this.getUser().getPrinter().export();
            this.setSuccess(true);
        } catch (Exception e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            this.getLockManager().releaseStructureReadLock();

        }
        return generateSuccessResponse();
    }

    @JsonProperty
    public void setExportSummary(boolean exportSummary) {
        this.exportSummary = exportSummary;
    }

    @JsonProperty
    public void setExportComment(boolean exportComment) {
        this.exportComment = exportComment;
    }

    @JsonProperty
    public boolean isExportSummary() {
        return exportSummary;
    }

    @JsonProperty
    public boolean isExportComment() {
        return exportComment;
    }
}
