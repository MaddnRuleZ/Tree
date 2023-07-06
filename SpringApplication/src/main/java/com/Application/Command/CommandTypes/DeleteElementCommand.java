package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;

import java.util.UUID;

public class DeleteElementCommand extends Command implements IEditorResponse {
    private UUID element;
    private boolean cascading;

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
