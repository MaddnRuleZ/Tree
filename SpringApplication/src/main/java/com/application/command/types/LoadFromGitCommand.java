package com.application.command.types;

import com.application.User;
import com.application.command.types.interfaces.ILoadCommand;
import com.application.exceptions.ProcessingException;
import com.application.printer.GitPrinter;
import com.application.printer.Printer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * command to load a tree from a git repository
 */
public class LoadFromGitCommand extends Command implements ILoadCommand {
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

    /**
     * path to the folder where the git repository should be cloned
     */
    private String path;

    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Printer printer = new GitPrinter(url, username, password, path);
            this.setSuccess(load(user, printer, path));
        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }
        return generateResponse(true);
    }


    @JsonProperty
    public User getUser() {
        return user;
    }

    @JsonProperty
    public String getUrl() {
        return url;
    }

    @JsonProperty
    public String getUsername() {
        return username;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setUser(User user) {
        this.user = user;
    }

    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    public void setPath(String path) {
        this.path = path;
    }
}
