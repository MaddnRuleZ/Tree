package com.application.printer;

import com.application.User;
import com.application.exceptions.OverleafGitException;
import com.application.exceptions.UnknownElementException;
import com.application.tree.interfaces.LaTeXTranslator;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Git Class for updating a Git -Overleaf repository with the Credentials
 */
public class GitPrinter extends Printer {
    private final String overleafUrl;
    private final String working_directory;
    private final CredentialsProvider credentialsProvider;
    private static final String MAIN_ENDING = "main.tex";

    /**
     * Constructs a new instance of the GitPrinter class with the specified parameters.
     *
     * @param overleafUrl   The URL of the Overleaf repository used for Git operations.
     * @param username      The username associated with the Git repository.
     * @param password      The password (or authentication token) used for accessing the Git repository.
     * @param workingDir    The working directory where Git operations will be performed.
     */
    public GitPrinter(String overleafUrl, String username, String password, String workingDir, User user) {
        super(workingDir, user);
        credentialsProvider = new UsernamePasswordCredentialsProvider(username, password);
        this.overleafUrl = overleafUrl;
        this.working_directory = workingDir;
        setFigurePath(this.working_directory);
    }

    /**
     * Clone or Pull the Repository, based on if it already exists or not
     *
     * @return true in case of success, else false
     * @throws OverleafGitException
     * @throws GitAPIException
     */
    public boolean pullOrCloneRepository() throws OverleafGitException, GitAPIException {
        if (pullRepository()) {
            return true;
        } else {
            return cloneRepository();
        }
    }

    /**
     * Fetch, Merge, Add Commit and then Push the new Changes
     *
     * @return
     * @throws OverleafGitException
     */
    public boolean commitAndPush() throws OverleafGitException {
        try {
            Git git = Git.open(new File(this.working_directory));
            fetch(git);
            merge(git);
            add(git);
            commit(git);
            push(git);

            return true;
        } catch (IOException ex) {
            throw new OverleafGitException("Fehler beim Öffnen des Repos (IO), pull zuerst" + ex.getMessage());
        } catch (GitAPIException ex) {
            throw new OverleafGitException("Fehler beim Ausführen von Git-Befehlen: " + ex.getMessage());
        }
    }

    private void fetch(Git git) throws GitAPIException {
        git.fetch().setCredentialsProvider(this.credentialsProvider).call();
    }

    private void merge(Git git) throws GitAPIException, IOException {
        git.merge().include(git.getRepository().resolve("origin/master")).call();
    }

    private void add(Git git) throws GitAPIException {
        git.add().addFilepattern(".").call();
    }

    private void commit(Git git) throws GitAPIException {
        git.commit().setMessage("Extern Overleaf Commit TreeX").call();
    }

    private void push(Git git) throws GitAPIException {
        git.push().setCredentialsProvider(this.credentialsProvider).setRemote(overleafUrl).call();
    }


    /**
     * Clone or Overwrite a Git repository from the specified URL into the working directory.
     */
    private boolean cloneRepository() throws OverleafGitException {
        File repositoryPath = new File(this.working_directory);
        if (repositoryPath.exists() && repositoryPath.isDirectory()) {
            deleteDirectoryRecursively(repositoryPath);
        }

        try {
            Git.cloneRepository()
                    .setURI(this.overleafUrl)
                    .setDirectory(repositoryPath)
                    .setCredentialsProvider(this.credentialsProvider)
                    .call();
            return true;
        } catch (GitAPIException ex) {
            throw new OverleafGitException("Fehler beim Klonen des Repositories: " + ex.getMessage());
        }
    }

    private boolean pullRepository() throws OverleafGitException {
        File repositoryPath = new File(this.working_directory);

        try (Git git = Git.open(repositoryPath)) {
            PullCommand pullCommand = git.pull()
                    .setCredentialsProvider(credentialsProvider);

            pullCommand.setStrategy(MergeStrategy.RESOLVE);
            pullCommand.call();

        } catch (IOException | GitAPIException ex) {
            return false;
        }
        return true;
    }

    private static boolean deleteDirectoryRecursively(File directory) {
        if (!directory.exists()) {
            return true;
        }

        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File file : contents) {
                if (file.isDirectory()) {
                    deleteDirectoryRecursively(file);
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    @Override
    public void export() throws IOException, UnknownElementException, OverleafGitException {
        Map<String, StringBuilder> map = new HashMap<>();
        map.put(this.getPath() + "/" + MAIN_ENDING, new StringBuilder());
        this.getUser().getRoot().toLaTeX(map, this.getPath()+ "/" + MAIN_ENDING, LaTeXTranslator.INIT_INDENTATION_LEVEL, this.isExportSummary(), this.isExportComments());
        for(String key : map.keySet()) {
            if (key == null) {
                throw new UnknownElementException(null, "File Path");
            }
            Files.writeString(Path.of(key), map.get(key));
        }
        this.commitAndPush();
    }
}
