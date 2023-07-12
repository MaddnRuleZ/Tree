package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.sectioning.Root;

import java.util.UUID;

public class EditCommentCommand implements Command, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private String comment;

    @Override
    public String execute() {
        //TODO
        return generateResponse();
    }

    @Override
    public String generateResponse() {
        return IEditorResponse.super.generateResponse();
    }

    public Root getRoot() {
        return root;
    }

    public UUID getElement() {
        return element;
    }

    public String getComment() {
        return comment;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
