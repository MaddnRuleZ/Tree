package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Command.CommandTypes.Interfaces.ITreeResponse;
import com.Application.Tree.elements.Root;

import java.util.UUID;

public class MoveElementTreeCommand implements Command, IMoveElementCommand, ITreeResponse {
    private Root root;
    private UUID element;
    private UUID newParent;
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

    public Root getRoot() {
        return root;
    }

    public UUID getNewParent() {
        return newParent;
    }

    public UUID getElement() {
        return element;
    }

    public UUID getPreviousElement() {
        return previousElement;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
