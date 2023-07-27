package com.application.command.types;

import com.application.exceptions.ParseException;
import com.application.exceptions.ProcessingException;
import com.application.interpreter.Parser;
import com.application.printer.GitPrinter;
import com.application.printer.Printer;
import com.application.tree.elements.roots.Root;
import com.application.tree.elements.roots.Roots;
import com.application.User;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * command to load a tree from a git repository
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

    /**
     * path to the folder where the git repository should be cloned
     */
    private String path;

    @Override
    public JsonNode execute() {
        try {
            acquireStructureWriteLock();
            Printer printer = new GitPrinter(url, username, password, path);

            Parser parser = new Parser(this.path);
            Roots root = parser.startParsing();
            if(root instanceof Root) {
                this.user.setRoot((Root) root);
                this.user.setPrinter(printer);
            } else {
                throw new ParseException("return value of parsing"+ root.getClass().getName());
            }
            this.setSuccess(true);

        } catch (ProcessingException e) {
            this.setSuccess(false);
            this.setFailureMessage(e.getMessage());
        } finally {
            releaseStructureWriteLock();
        }
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
