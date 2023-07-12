package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Command.CommandTypes.Interfaces.IMoveElementCommand;
import com.Application.Tree.elements.parents.sectioning.Root;

import java.util.UUID;

public class MoveElementEditorCommand implements Command, IMoveElementCommand, IEditorResponse, ILocks {
    private Root root;
    private UUID element;
    private UUID newParent;
    private UUID previousElement;

    @Override
    public String execute() {
        //TODO
        return generateResponse();
    }

    @Override
    public String generateResponse() {
        return IEditorResponse.super.generateResponse();
    }

    @Override
    public boolean moveElement() {
        return IMoveElementCommand.super.moveElement();
    }

    public Root getRoot() {
        return root;
    }

    public UUID getElement() {
        return element;
    }

    public UUID getNewParent() {
        return newParent;
    }

    public UUID getPreviousElement() {
        return previousElement;
    }

    public void setRoot(Root root) {
        this.root = root;
    }
}
