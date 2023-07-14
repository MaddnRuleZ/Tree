package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Command implements ILocks {
    private boolean success = false;
    private String failureMessage = null;

    /**
     * executes the command
     */
    public abstract JsonNode execute();


    /**
     * generates the Json response String, if execution failed
     * @return failureResponse
     */
    JsonNode generateFailureResponse(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("error", message);
        return rootNode;
    }

    public abstract JsonNode generateResponse();

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
    }
}
