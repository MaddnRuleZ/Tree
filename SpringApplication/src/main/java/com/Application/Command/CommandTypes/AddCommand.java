package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Tree.elements.parents.sectioning.Root;

import java.util.UUID;

public class AddCommand implements Command, IEditorResponse, ILocks {


    private Root root;
    private String content;
    private UUID parent;
    private UUID previousChild;


    @Override
    public String execute() {
        //TODO
        return generateResponse();
    }

    @Override
    public String generateResponse() {
        String response;
        try {
            acquireStructureReadLock();
            response = IEditorResponse.super.generateResponse();
            releaseStructureReadLock();
        } catch (Exception e) {
            releaseStructureReadLock();
            response = "Error: " + e.getMessage();
        }
        return response;
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
