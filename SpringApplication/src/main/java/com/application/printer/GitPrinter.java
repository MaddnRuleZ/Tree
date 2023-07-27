package com.application.printer;

import com.application.exceptions.UnknownElementException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.RebaseCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;

import java.io.IOException;

/**
 * Git Class for updating a Git -Overleaf repository with the Credentials
 */
public class GitPrinter extends Printer {
    private final String overleafUrl;
    private final String working_directory;
    private final CredentialsProvider credentialsProvider;

    /**
     * Constructs a new instance of the GitPrinter class with the specified parameters.
     *
     * @param overleafUrl The URL of the Overleaf repository used for Git operations.
     * @param username The username associated with the Git repository.
     * @param password The password (or authentication token) used for accessing the Git repository.
     * @param workingDir The working directory where Git operations will be performed.
     */
    public GitPrinter(String overleafUrl, String username, String password, String workingDir) {
        credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        this.overleafUrl = overleafUrl;
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

        try {
            Git.cloneRepository()
                    .setURI(this.overleafUrl)
                    .setDirectory(repositoryPath)
                    .setCredentialsProvider(this.credentialsProvider)
                    .call();
            return true;
        } catch (GitAPIException e) {
            return false;
        }
    }

    /**
     * Rebase the changes from the specified remote branch onto the current working branch.
     *
     * @param remoteBranch The name of the remote branch to rebase onto the current working branch.
     * @return True if the rebase operation was successful, false otherwise.
     */
    public boolean rebaseChanges(String remoteBranch) {
        try {
            Git git = Git.open(new File(this.working_directory));
            git.fetch()
                    .setCredentialsProvider(this.credentialsProvider)
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

    /**
     * Pushes the local changes to the remote Git repository.
     *
     * @return True if the push operation was successful, false otherwise.
     */
    public boolean pushChanges() {
        String refSpec = "refs/heads/master:refs/heads/master";

        try {
            Git git = Git.open(new File(this.working_directory));
            git.push()
                    .setCredentialsProvider(this.credentialsProvider)
                    .setRemote(overleafUrl)
                    .setRefSpecs(new RefSpec(refSpec))
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
                PullResult pullResult = git.pull()
                        .setCredentialsProvider(credentialsProvider).call();

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
     * Checks if the remote repository was changed compared to the local repository.
     *
     * @param remoteBranch The name of the remote branch to compare against the local branch.
     * @return True if the remote repository was changed, false otherwise.
     * @throws IOException  If an I/O error occurs while accessing the repository.
     * @throws GitAPIException If an error occurs during Git operations.
     */
    public boolean isRemoteRepositoryChanged(String remoteBranch) throws IOException, GitAPIException {
        Repository repository = new FileRepositoryBuilder()
                .setGitDir(new File(this.working_directory, ".git"))
                .build();

        Git git = new Git(repository);

        FetchResult fetchResult = git.fetch()
                .setCredentialsProvider(this.credentialsProvider)
                .setRemote(overleafUrl)
                .call();

        boolean hasChanges = !fetchResult.getTrackingRefUpdates().isEmpty();

        if (!hasChanges) {
            return false;
        } else {
            String localCommitId = repository.resolve("HEAD").getName();

            try {
                String remoteCommitId = repository.resolve("refs/remotes/origin/" + remoteBranch).getName();
                return !localCommitId.equals(remoteCommitId);
            } catch (Exception e) {
                return true;
            }
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
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        directory.delete();
    }

    @Override
    public void export() throws IOException, UnknownElementException {
        //TODO
    }
}
