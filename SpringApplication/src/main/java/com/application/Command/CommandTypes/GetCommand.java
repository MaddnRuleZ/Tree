package com.application.Command.CommandTypes;

import com.application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Command for generating Response
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
