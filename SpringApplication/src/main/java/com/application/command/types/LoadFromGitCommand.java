package com.application.command.types;

import com.application.command.types.interfaces.ILoadCommand;
import com.application.exceptions.OverleafGitException;
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
        this.getLockManager().acquireStructureWriteLock();
        try {
            this.getUser().resetUser();
            GitPrinter printer = new GitPrinter(url, username, password, path, this.getUser());
            printer.executeCurl();

            load(this.getUser(), printer, path);
            this.setSuccess(true);

         } catch (OverleafGitException overleafGitException) {


        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            this.getLockManager().releaseStructureWriteLock();
        }
        return generateResponse(true);
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
