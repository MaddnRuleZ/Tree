package com.Application.Printer;

import com.Application.Exceptions.UnknownElementException;
import com.Application.User;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

import java.io.IOException;

public class GitPrinter extends Printer {
    /**
     *  user that holds information of LaTeX-Project
     */
    private User user;
    /**
     * url to the git repository
     */
    private String overleafUrl;
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
    private String working_directory;


    private CredentialsProvider credentialsProvider;

    public GitPrinter(User user, String overleafUrl, String username, String password, String workingDir) {
        credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        this.user = user;
        this.overleafUrl = overleafUrl;
        this.username = username;
        this.password = password;
        this.working_directory = workingDir;
        System.out.println(workingDir);
    }

    /**
     * Clone or Overwrite a Git repository from the specified URL into the working directory.
     */
    public boolean cloneRepository() {
        File repositoryPath = new File(this.working_directory);
        if (repositoryPath.exists() && repositoryPath.isDirectory()) {
            deleteDirectory(repositoryPath);
        }

        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
        try {
            Git.cloneRepository()
                    .setURI(this.overleafUrl)
                    .setDirectory(repositoryPath)
                    .setCredentialsProvider(credsProvider)
                    .call();
            return true;
        } catch (GitAPIException e) {
            return false;
        }
    }

    public boolean rebaseChanges(String remoteBranch) {
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(username, password);

        try {
            Git git = Git.open(new File(this.working_directory));
            git.fetch()
                    .setCredentialsProvider(credsProvider)
                    .setRemote(overleafUrl)
                    .call();


            RebaseCommand rebaseCommand = git.rebase();
            rebaseCommand.setUpstream(remoteBranch);

            rebaseCommand.call();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean pushChanges() {
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(username, password);

        try {
            Git git = Git.open(new File(this.working_directory));
            git.push()
                    .setCredentialsProvider(credsProvider)
                    .setRemote(overleafUrl)
                    .setRefSpecs(new RefSpec("refs/heads/master:refs/heads/master"))
                    .call();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Pull changes from the remote repository and update the local version.
     */
    public boolean pullRepository() {
        File repositoryPath = new File(this.working_directory);

        if (repositoryPath.exists() && repositoryPath.isDirectory()) {
            try {
                Git git = Git.open(repositoryPath);
                CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
                PullResult pullResult = git.pull().setCredentialsProvider(credsProvider).call();

                if (!pullResult.isSuccessful()) {
                    System.out.println("Unable to update the repository.");
                    return false;

                } else if (pullResult.getMergeResult() != null && pullResult.getMergeResult().getConflicts() != null) {
                    System.out.println("There are merge conflicts that need to be resolved.");
                    return false;

                } else {
                    System.out.println("Repository updated successfully.");
                    return true;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Local repository does not exist or is not a valid repository.");
            return false;
        }
        return false;
    }

    /**
     * Helper method to recursively delete a directory and its contents.
     *
     * @param directory        The directory to be deleted.
     */
    private void deleteDirectory(File directory) {
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }


    public void setPassword(String newPassword) {
        this.credentialsProvider = new UsernamePasswordCredentialsProvider(this.username, newPassword);
        this.password = newPassword;
    }



    public void setUsername(String newUsername) {
        this.credentialsProvider = new UsernamePasswordCredentialsProvider(newUsername, this.password);
        this.username = newUsername;
    }

    @Override
    public void export() throws IOException, UnknownElementException {
        //TODO
    }
}
