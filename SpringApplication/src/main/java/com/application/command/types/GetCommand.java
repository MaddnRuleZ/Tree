package com.application.command.types;

import com.application.tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * command for generating Response
 */
public class GetCommand extends Command {
    /**
     * Indicates whether the response is for the editor or the tree
     */
    private final boolean isEditorGet;


    public GetCommand(Root root, boolean isEditorGet) {
        this.isEditorGet = isEditorGet;
        this.setRoot(root);
    }

    @Override
    public JsonNode execute() {
        return generateResponse(isEditorGet);
    }
}