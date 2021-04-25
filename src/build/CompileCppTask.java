package eu.menzani.build;

import eu.menzani.io.PrintToConsoleException;
import eu.menzani.misc.NativeLibrary;
import eu.menzani.system.Platform;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <b>CentOS 7</b>
 *
 * <pre>{@code
 * yum install gcc glibc-devel.i686 libgcc.i686
 * yum install java-11-openjdk-devel
 * }</pre>
 *
 * <b>Windows 10</b>
 * <p>
 * Install Visual Studio 2019 components:
 *
 * <pre>{@code
 * MSVC - VS C++ x64/x86 build tools
 * Windows 10 SDK
 * }</pre>
 */
public class CompileCppTask {
    public static void run(boolean shouldRebuild) {
        CompileCppTask compileCppTask = new CompileCppTask();
        compileCppTask.checkRequirements();
        compileCppTask.compileCppInCurrentProject(shouldRebuild);
    }

    private CompileCppTask() {
    }

    private void checkRequirements() {
        if (!Platform.current().is64Bit()) {
            throw new PrintToConsoleException("Platform must be 64-bit.");
        }
    }

    private void compileCppInCurrentProject(boolean shouldRebuild) {
        for (IdeaModule module : IdeaProject.current().getModules()) {
            Path nativeSourceFolder = module.getNativeSourceFolder();
            if (Files.exists(nativeSourceFolder)) {

                Path outputFileWithoutExtension = module
                        .getProductionOutputDirectory()
                        .resolve(NativeLibrary.FOLDER_IN_ARTIFACT)
                        .resolve(module.getNameAsFolder());

                CppCompiler cppCompiler = new CppCompiler(
                        nativeSourceFolder,
                        module.getNativeOutputDirectory(),
                        outputFileWithoutExtension,
                        module.getName(),
                        shouldRebuild
                );
                cppCompiler.compile();
            }
        }
    }
}
