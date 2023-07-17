package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Command implements ILocks {
    /**
     * indicates success of processing the command
     */
    private boolean success = false;
    /**
     * failure message, if success is false
     */
    private String failureMessage = null;

    /**
     * executes the command
     * @return JsonNode of response
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


    /**
     * generates Response for Frontend
     * If failure, generateFailureResponse is called
     * @return JsonNode of response
     */
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
