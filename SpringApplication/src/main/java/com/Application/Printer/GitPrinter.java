package com.Application.Printer;

import com.Application.Exceptions.UnknownElementException;
import com.Application.User;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
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

    public GitPrinter(User user, String overleafUrl, String username, String password, String workingDir) {
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
    public void cloneRepository() {
        // Check if the directory already exists, and if so, delete it before cloning the repository
        File repositoryPath = new File(this.working_directory);
        if (repositoryPath.exists() && repositoryPath.isDirectory()) {
            deleteDirectory(repositoryPath);
        }

        // Set up credentials for authentication (if required)
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);
        try {
            // Clone the repository from the specified URL into the working directory
            Git.cloneRepository()
                    .setURI(this.overleafUrl)
                    .setDirectory(repositoryPath)
                    .setCredentialsProvider(credsProvider)
                    .call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
    }

    public void pushChanges() {
        CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(username, password);

        try {
            Git git = Git.open(new File(this.working_directory));
            git.push()
                    .setCredentialsProvider(credsProvider)
                    .setRemote(overleafUrl)
                    .setRefSpecs(new RefSpec("refs/heads/master:refs/heads/master"))
                    .call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Pull changes from the remote repository and update the local version.
     */
    public void pullRepository() {
        File repositoryPath = new File(this.working_directory);

        // Check if the directory exists and is a valid repository
        if (repositoryPath.exists() && repositoryPath.isDirectory()) {
            try {
                // Open the existing repository
                Git git = Git.open(repositoryPath);
                // Set up credentials for authentication (if required)
                CredentialsProvider credsProvider = new UsernamePasswordCredentialsProvider(this.username, this.password);

                // Perform a pull operation to fetch and merge changes from the remote repository
                PullResult pullResult = git.pull().setCredentialsProvider(credsProvider).call();

                // Check the result of the pull operation
                if (!pullResult.isSuccessful()) {
                    System.out.println("Unable to update the repository.");
                } else if (pullResult.getMergeResult() != null && pullResult.getMergeResult().getConflicts() != null) {
                    System.out.println("There are merge conflicts that need to be resolved.");
                } else {
                    System.out.println("Repository updated successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Local repository does not exist or is not a valid repository.");
        }
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
                    // Recursively delete subdirectories and their contents
                    deleteDirectory(file);
                } else {
                    // Delete individual files
                    file.delete();
                }
            }
        }
        // Delete the empty directory after its contents have been removed
        directory.delete();
    }


    @Override
    public void export() throws IOException, UnknownElementException {
        //TODO

    }
}
