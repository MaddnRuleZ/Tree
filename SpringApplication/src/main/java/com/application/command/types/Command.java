package com.application.command.types;

import com.application.User;
import com.application.command.LockManager;
import com.application.exceptions.FailureResponse;
import com.application.exceptions.GeneratingResponseException;
import com.application.exceptions.ProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * abstract class for all commands
 * provides success and failure message
 * provides execute and generateResponse
 */
@SuppressWarnings("CanBeFinal")
public abstract class Command {

    private final LockManager lockManager = LockManager.getInstance();
    /**
     * indicates success of processing the command
     */
    private boolean success = false;
    /**
     * failure message, if success is false
     */
    private String failureMessage = null;

    private User user;

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
            lockManager.acquireStructureReadLock();
            try {
                if(isEditorResponse) {
                    response = this.user.getRoot().toJsonEditor();
                } else {
                    response = this.user.getRoot().toJsonTree();
                }
            } catch (NullPointerException | ProcessingException e) {
                response = FailureResponse.generateFailureResponse(new GeneratingResponseException().getMessage());
                this.setSuccess(false);
            } finally {
                lockManager.releaseStructureReadLock();
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
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public LockManager getLockManager() {
        return lockManager;
    }
}
