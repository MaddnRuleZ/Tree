package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;

import java.util.UUID;

public class AddCommand extends Command implements IEditorResponse {
    //private final Root root;
    private UUID parent;
    private UUID previousElement;
    private String content;


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
}
