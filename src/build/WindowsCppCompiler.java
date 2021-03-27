package eu.menzani.build;

import eu.menzani.io.PrintToConsoleException;
import eu.menzani.io.ProcessLauncher;
import eu.menzani.struct.FileExtension;
import eu.menzani.system.Platform;
import eu.menzani.system.SystemProperty;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

class WindowsCppCompiler {
    private static final Path vcvarsallPath = findVcvarsallPath();
    private static final String cppFileErrorLineContains = FileExtension.CPP.getSuffixWithDot() + '(';
    private static final String cFileErrorLineContains = FileExtension.C.getSuffixWithDot() + '(';
    private static final String hFileErrorLineContains = FileExtension.H.getSuffixWithDot() + '(';

    private static Path findVcvarsallPath() {
        final Path fromBuildTools = Path.of("C:", "Program Files (x86)", "Microsoft Visual Studio", "2019", "BuildTools", "VC", "Auxiliary", "Build", "vcvarsall.bat");
        if (Files.exists(fromBuildTools)) {
            return fromBuildTools;
        }
        final Path fromCommunity = Path.of("C:", "Program Files (x86)", "Microsoft Visual Studio", "2019", "Community", "VC", "Auxiliary", "Build", "vcvarsall.bat");
        if (Files.exists(fromCommunity)) {
            return fromCommunity;
        }
        final Path fromProfessional = Path.of("C:", "Program Files (x86)", "Microsoft Visual Studio", "2019", "Professional", "VC", "Auxiliary", "Build", "vcvarsall.bat");
        if (Files.exists(fromProfessional)) {
            return fromProfessional;
        }
        throw new PrintToConsoleException("Could not find vcvarsall.bat file.");
    }

    private final Collection<Path> sources;
    private final Path workingDirectory;
    private final Path outputFileWithoutExtension;

    WindowsCppCompiler(Collection<Path> sources, Path workingDirectory, Path outputFileWithoutExtension) {
        this.sources = sources;
        this.workingDirectory = workingDirectory;
        this.outputFileWithoutExtension = outputFileWithoutExtension;
    }

    void invokeCl() throws IOException {
        if (invokeCl(Platform.Architecture.BIT_32)) return;
        invokeCl(Platform.Architecture.BIT_64);
    }

    private boolean invokeCl(Platform.Architecture architecture) throws IOException {
        ProcessLauncher launcher = new ProcessLauncher("cmd");
        launcher.setWorkingDirectory(workingDirectory);
        launcher.start();
        launcher.beginWrite();

        launcher.writeLine("call \"" + vcvarsallPath + "\" " + (architecture == Platform.Architecture.BIT_32 ? "x86" : "x64"));

        Path includeFolder = SystemProperty.JAVA_HOME.resolve("include");
        String outputFile = outputFileWithoutExtension.toAbsolutePath() + CppCompiler.outputFileSuffixFor(architecture);
        StringBuilder builder = new StringBuilder(
                "cl.exe /I \"" + includeFolder + "\" /I \"" + includeFolder.resolve("win32") + "\" /Fe\"" + outputFile + "\" /LD");
        for (Path source : sources) {
            builder.append(' ');
            builder.append(source.toAbsolutePath().toString());
        }
        launcher.writeLine(builder.toString());

        launcher.endWrite();

        boolean errorsOccurred = false;
        for (String line : launcher.outputIterator()) {
            int indexOfCppExtension = line.indexOf(cppFileErrorLineContains);
            int indexOfCExtension = line.indexOf(cFileErrorLineContains);
            int indexOfHExtension = line.indexOf(hFileErrorLineContains);
            if (indexOfCppExtension == -1 && indexOfCExtension == -1 && indexOfHExtension == -1) continue;
            int indexOfExtension;
            int extensionLength;
            if (indexOfCppExtension == -1) {
                if (indexOfCExtension == -1) {
                    indexOfExtension = indexOfHExtension;
                } else {
                    indexOfExtension = indexOfCExtension;
                }
                extensionLength = 3;
            } else {
                assert indexOfCExtension == -1 && indexOfHExtension == -1;
                indexOfExtension = indexOfCppExtension;
                extensionLength = 5;
            }
            int indexOfClosingBracket = line.indexOf("):", indexOfExtension);
            try {
                Integer.parseInt(line, indexOfExtension + extensionLength, indexOfClosingBracket, 10);
            } catch (NumberFormatException e) {
                continue;
            }
            System.err.println(line);
            errorsOccurred = true;
        }

        removeUnneededOutput(outputFile);
        return errorsOccurred;
    }

    private static void removeUnneededOutput(String outputFile) throws IOException {
        Path expFile = Path.of(outputFile + ".exp");
        Path libFile = Path.of(outputFile + ".lib");
        Files.deleteIfExists(expFile);
        Files.deleteIfExists(libFile);
    }
}
