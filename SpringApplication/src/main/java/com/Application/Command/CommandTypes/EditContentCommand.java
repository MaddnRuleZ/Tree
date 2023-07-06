package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;

import java.util.UUID;

public class EditContentCommand extends Command implements IEditorResponse {
    //private Root root;
    private UUID element;
    private UUID content;

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
