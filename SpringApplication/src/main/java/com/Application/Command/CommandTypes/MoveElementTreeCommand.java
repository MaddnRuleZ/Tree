package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Command.CommandTypes.Interfaces.ITreeResponse;

import java.util.UUID;

public class MoveElementTreeCommand extends Command implements IMoveElementCommand, ITreeResponse {
    // private Root root;
    private String type;
    private UUID parent;
    private UUID previousElement;


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

    @Override
    public boolean moveElement() {
        return IMoveElementCommand.super.moveElement();
    }
}
