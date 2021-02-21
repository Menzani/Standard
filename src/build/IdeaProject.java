package eu.menzani.build;

import eu.menzani.concurrent.Lazy;
import eu.menzani.lang.UncaughtException;
import eu.menzani.misc.XmlParser;
import eu.menzani.struct.AppInfo;
import eu.menzani.system.SystemPaths;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class IdeaProject {
    private static final Lazy<IdeaProject> current = Lazy.of(() -> new IdeaProject(SystemPaths.WORKING_DIRECTORY));

    public static IdeaProject current() {
        return current.get();
    }

    static final String IDEA_FOLDER_NAME = ".idea";

    private final Path directory;
    private final Path ideaFolder;
    private final String name;
    private final List<IdeaModule> modules;

    public IdeaProject(Path directory) {
        this.directory = directory;
        ideaFolder = directory.resolve(IDEA_FOLDER_NAME);
        checkIdeaFolder();
        name = directory.getFileName().toString();
        try {
            modules = readModules();
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    private void checkIdeaFolder() {
        if (Files.notExists(ideaFolder)) {
            throw new IdeaProjectException("Could not find", directory);
        }
    }

    private List<IdeaModule> readModules() throws IOException {
        IdeaProjectDescriptorScanner projectDescriptorScanner = new IdeaProjectDescriptorScanner();
        XmlParser.parse(ideaFolder.resolve("misc.xml"), projectDescriptorScanner);
        Path projectOutputDirectory = projectDescriptorScanner.getOutputDirectory();

        IdeaProjectScanner projectScanner = new IdeaProjectScanner(name);
        Files.walkFileTree(directory, projectScanner);
        return projectScanner.getModules(projectOutputDirectory);
    }

    public Path getDirectory() {
        return directory;
    }

    public String getName() {
        return name;
    }

    List<IdeaModule> getModules() {
        return modules;
    }

    public boolean isNotLibrary() {
        return name.equals(AppInfo.getProjectName());
    }
}
