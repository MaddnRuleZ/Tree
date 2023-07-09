package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Tree.elements.Root;

import java.util.UUID;

public class MoveElementEditorCommand implements Command, IMoveElementCommand, IEditorResponse {
    private Root root;
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
