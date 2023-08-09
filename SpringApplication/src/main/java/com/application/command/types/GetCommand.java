package com.application.command.types;

import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * command for generating Response
 */
public class GetCommand extends Command {
    /**
     * Indicates whether the response is for the editor or the tree
     */
    private final boolean isEditorGet;


    public GetCommand(User user, boolean isEditorGet) {
        this.isEditorGet = isEditorGet;
        this.setUser(user);
    }

    @Override
    public JsonNode execute() {
        this.setSuccess(true);
        return generateResponse(isEditorGet);
    }
}
