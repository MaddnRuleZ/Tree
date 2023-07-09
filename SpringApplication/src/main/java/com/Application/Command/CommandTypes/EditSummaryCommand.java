package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Tree.elements.Root;

import java.util.UUID;

public class EditSummaryCommand implements Command, IEditorResponse {
    private Root root;
    private UUID element;
    private UUID summary;

    @Override
    public String execute() {
        //TODO
        return null;
    }


    @Override
    public String generateResponse() {
        //TODO
        return null;
    }
    public void setRoot(Root root) {
        this.root = root;
    }

    public Root getRoot() {
        return root;
    }

    public UUID getElement() {
        return element;
    }

    public UUID getSummary() {
        return summary;
    }
}
