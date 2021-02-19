package eu.menzani.build;

import eu.menzani.io.PrintToConsoleException;
import eu.menzani.io.SimpleDirectoryStreamFilter;
import eu.menzani.lang.UncaughtException;
import eu.menzani.struct.FileExtension;
import eu.menzani.system.Platform;
import eu.menzani.system.PlatformFamilyDependant;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class CppCompiler {
    private static final SimpleDirectoryStreamFilter.Builder filterBuilder = SimpleDirectoryStreamFilter.builder()
            .orHasExtension(FileExtension.CPP)
            .orHasExtension(FileExtension.C);

    private final Path sourcesFolder;
    private final Path outputFolder;
    private final Path outputFileWithoutExtension;

    CppCompiler(Path sourcesFolder, Path outputFolder, Path outputFileWithoutExtension) {
        this.sourcesFolder = sourcesFolder;
        this.outputFolder = outputFolder;
        this.outputFileWithoutExtension = outputFileWithoutExtension;
    }

    void createOutputFolders() throws IOException {
        Files.createDirectories(outputFolder);
        Files.createDirectories(outputFileWithoutExtension.getParent());
    }

    void compile() {
        new Compile();
    }

    private class Compile extends PlatformFamilyDependant {
        @Override
        protected void onWindows() {
            try {
                Set<Path> sources = findSources("windows");
                WindowsCppCompiler compiler = new WindowsCppCompiler(sources, outputFolder, outputFileWithoutExtension);
                compiler.invokeCl();
            } catch (IOException e) {
                throw new UncaughtException(e);
            }
        }

        private Set<Path> findSources(String fileNameSuffix) throws IOException {
            SimpleDirectoryStreamFilter filter = filterBuilder.orMustEndWith('_' + fileNameSuffix).build();
            Set<Path> sources = new HashSet<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcesFolder, filter)) {
                for (Path path : stream) {
                    sources.add(path);
                }
            }
            if (sources.isEmpty()) {
                throw new PrintToConsoleException("No sources found.");
            }
            return Collections.unmodifiableSet(sources);
        }
    }

    static String outputFileSuffixFor(Platform.Architecture architecture) {
        switch (architecture) {
            case BIT_32:
                return "_32";
            case BIT_64:
                return "_64";
            default:
                throw new AssertionError();
        }
    }
}
