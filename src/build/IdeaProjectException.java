package eu.menzani.build;

import java.nio.file.Path;

public class IdeaProjectException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    private final Path directory;

    IdeaProjectException(String message, Path directory) {
        super(message + " IntelliJ IDEA project at \"" + directory + "\".");
        this.directory = directory;
    }

    public Path getDirectory() {
        return directory;
    }
}
