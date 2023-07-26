package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.ILocks;
import com.Application.Exceptions.FailureResponse;
import com.Application.Exceptions.GeneratingResponseException;
import com.Application.Exceptions.ProcessingException;
import com.Application.Tree.elements.roots.Root;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * abstract class for all commands
 * provides success and failure message
 * provides execute and generateResponse
 */
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
     * the root of the tree structure to be processed
     */
    private Root root;

    /**
     * executes the command
     * @return JsonNode of response
     */
    public abstract JsonNode execute();


    /**
     * generates response for frontend
     * If failure, generateFailureResponse is called
     * @return JsonNode of response
     */
    public JsonNode generateResponse(boolean isEditorResponse) {
        JsonNode response;
        if (this.isSuccess()) {
            try {
                acquireStructureReadLock();
                if(isEditorResponse) {
                    response = root.toJsonEditor();
                } else {
                    response = root.toJsonTree();
                }
            } catch (NullPointerException e) {
                response = FailureResponse.generateFailureResponse(new GeneratingResponseException().getMessage());
                this.setSuccess(false);
            } finally {
                releaseStructureReadLock();
            }
        } else {
            response = FailureResponse.generateFailureResponse(this.getFailureMessage());
        }
        return response;
    }

    /**
     * generates response containing if command was successful
     * @return JsonNode of response
     */
    public JsonNode generateSuccessResponse() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode response = mapper.createObjectNode();
        if(this.isSuccess()) {
            response.put("success", true);
        } else {
            response.put("success", false);
            response.put("failureMessage", this.getFailureMessage());
        }
        return response;
    }

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
    public void setRoot(Root root) {
        this.root = root;
    }
}
