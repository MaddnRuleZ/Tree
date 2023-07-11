package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.Root;

import java.util.UUID;

public class EditSummaryCommand implements Command, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private UUID summary;

    @Override
    public boolean execute() {
        //TODO
        return false;
    }


    @Override
    public String generateResponse() {
        return IEditorResponse.super.generateResponse();
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
