package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Tree.elements.Root;

import java.util.UUID;

public class AddCommand implements Command, IEditorResponse {


    private Root root;
    private String content;
    private UUID parent;
    private UUID previousChild;


    @Override
    public String execute() {
        //TODO
        return null;
    }

    @Override
    public String generateResponse() {
        //TODO
        return "";
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
}
