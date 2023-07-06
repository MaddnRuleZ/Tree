package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;

import java.util.UUID;

public class EditSummaryCommand extends Command implements IEditorResponse {
    //private Root root;
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
}
