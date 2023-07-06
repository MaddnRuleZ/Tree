package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;

import java.util.UUID;

public class AddCommand extends Command implements IEditorResponse {
    //private Root root;
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


}
