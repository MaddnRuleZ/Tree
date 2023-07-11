package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.Root;

import java.util.UUID;

public class AddCommand implements Command, IEditorResponse, ILocks {


    private Root root;
    private String content;
    private UUID parent;
    private UUID previousChild;


    @Override
    public boolean execute() {
        //TODO
        return false;
    }



    public String getContent() {
        return content;
    }

    public UUID getParent() {
        return parent;
    }
    public UUID getPreviousChild() {
        return previousChild;
    }

    public Root getRoot() {
        return root;
    }

    public void setRoot(Root root) {
        this.root = root;
    }

    @Override
    public String generateResponse() {
        return IEditorResponse.super.generateResponse();
    }
}
