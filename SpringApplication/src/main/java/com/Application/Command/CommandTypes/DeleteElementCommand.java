package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.Root;

import java.util.UUID;

public class DeleteElementCommand implements Command, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private boolean cascading;

    @Override
    public String execute() {
        //TODO
        return null;
    }

    @Override
    public String generateResponse() {
        return IEditorResponse.super.generateResponse();
    }

    public UUID getElement() {
        return element;
    }

    public boolean isCascading() {
        return cascading;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
