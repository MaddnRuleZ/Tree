package com.Application.Command.CommandTypes;

import com.Application.Command.CommandTypes.Interfaces.IEditorResponse;
import com.Application.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Command to load a tree from a git repository
 */
public class LoadFromGitCommand extends Command {
    /**
     *  user that holds information of LaTeX-Project
     */
   private User user;
   /**
    * url to the git repository
    */
   private String url;
   /**
    * username for the git repository
    */
   private String username;
   /**
    * password for the git repository
    */
   private String password;

    @Override
    public JsonNode execute() {
        //TODO
        return generateResponse(true);
    }


    public User getUser() {
        return user;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
