package com.application.printer;

import com.application.User;
import com.application.exceptions.OverleafGitException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.interfaces.LaTeXTranslator;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Git Class for updating a Git -Overleaf repository with JGIT
 *
 */
public class GitPrinter extends Printer {
    private final String overleafUrl;
    private final String working_directory;
    private final CredentialsProvider credentialsProvider;
    private static final String MAIN_ENDING = "main.tex";
    private static final String DEFAULT_COMMIT_MSG = "Extern Overleaf Commit TreeX";

    /**
     * Constructs a new instance of the GitPrinter class with the specified parameters.
     *
     * @param overleafUrl   The URL of the Overleaf repository used for Git operations.
     * @param username      The username associated with the Git repository.
     * @param password      The password used for accessing the Git repository.
     * @param workingDir    The working directory where Git operations will be performed.
     */
    public GitPrinter(String overleafUrl, String username, String password, String workingDir, User user) {
        super(workingDir + "/" + MAIN_ENDING, user);
        credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        this.overleafUrl = overleafUrl;
        this.working_directory = workingDir;
        setDirectoryPath(this.working_directory);
    }

    /**
     * Pull Repo if already exists, else clone the Repo from Overleaf-Git
     * working_directory will be deleted in 'clone' scenario
     *
     * @throws OverleafGitException
     */
    public void pullOrCloneRepository() throws OverleafGitException {
        if (pullRepository()) {
        } else {
            cloneRepository();
        }
    }

    /**
     * Push the Changes to the Remote Repository,
     * do in Order:
     * Fetch, Merge, Add Commit and then Push the new Changes to given Repository
     *
     * @throws OverleafGitException throws Exception on Upload Error
     */
    public void commitAndPush() throws OverleafGitException {
        try {
            Git git = Git.open(new File(this.working_directory));
            git.rebase().setUpstream("origin/master");
            git.add().addFilepattern(".").call();
            git.commit().setMessage(DEFAULT_COMMIT_MSG).call();
            git.push().setCredentialsProvider(this.credentialsProvider).setRemote(overleafUrl).call();

        } catch (JGitInternalException ex) {
            throw new OverleafGitException(ex.getMessage());
        } catch (IOException ex) {
            throw new OverleafGitException("Fehler beim Öffnen des Repos (IO), pull zuerst" + ex.getMessage());
        } catch (GitAPIException ex) {
            throw new OverleafGitException("Fehler beim Ausführen von Git-Befehlen: " + ex.getMessage());
        }
    }

    /**
     * Clone or Overwrite a Git repository from the specified URL into the working directory.
     */
    private boolean cloneRepository() throws OverleafGitException {
        File repositoryPath = new File(this.working_directory);

        try {
            Git.cloneRepository()
                    .setURI(this.overleafUrl)
                    .setDirectory(repositoryPath)
                    .setCredentialsProvider(this.credentialsProvider)
                    .call();
            return true;
        } catch (GitAPIException | JGitInternalException ex) {
            throw new OverleafGitException("Fehler beim Klonen des Repositories: " + ex.getMessage());
        }
    }

    public boolean isRemoteChanged() {
        try {
            FileRepository localRepo = new FileRepository(working_directory + "/.git");
            Git git = new Git(localRepo);

            FetchResult fetchResult = git.fetch().setCredentialsProvider(credentialsProvider)
                    .setRemote(overleafUrl)
                    .setRefSpecs(new RefSpec("+refs/heads/*:refs/remotes/origin/*"))
                    .call();

            Collection<TrackingRefUpdate> trackingRefUpdates = fetchResult.getTrackingRefUpdates();

            if (!trackingRefUpdates.isEmpty()) {
                String remoteBranch = trackingRefUpdates.iterator().next().getRemoteName();
                ObjectId remoteHead = fetchResult.getAdvertisedRef(remoteBranch).getObjectId();

                RevWalk revWalk = new RevWalk(localRepo);
                RevCommit commit = revWalk.parseCommit(remoteHead);
                String commitMessage = commit.getFullMessage();
                System.out.println("Last commit message on remote repository: " + commitMessage);
                revWalk.dispose();
                return !commitMessage.equals(DEFAULT_COMMIT_MSG);
            }
        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * git pull the Repository from the Overleaf -GitRepo
     * resolves merge conflicts
     *
     * @return true in case of success, on Error return false
     */
    public boolean pullRepository() {
        File repositoryPath = new File(this.working_directory);

        try (Git git = Git.open(repositoryPath)) {
            PullCommand pullCommand = git.pull().setCredentialsProvider(credentialsProvider);
            pullCommand.setStrategy(MergeStrategy.RESOLVE);
            pullCommand.call();

            return true;

        } catch (IOException | GitAPIException | JGitInternalException exception) {
            return false;
        }
    }

    @Override
    public void export() throws IOException, UnknownElementException, OverleafGitException {
        print();
        this.commitAndPush();

        System.out.println("Exported to Overleaf Git Repository");
    }

    public void print() throws UnknownElementException, IOException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put(this.getPath(), new StringBuilder());
        this.getUser().getRoot().toLaTeX(map, this.getPath(), LaTeXTranslator.INIT_INDENTATION_LEVEL, this.isExportSummary(), this.isExportComments());
        for(String key : map.keySet()) {
            if (key == null) {
                throw new UnknownElementException(null, "File Path");
            }
            Files.writeString(Path.of(key), map.get(key));
        }
    }
}
