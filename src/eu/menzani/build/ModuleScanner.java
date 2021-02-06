package eu.menzani.build;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

class ModuleScanner extends SimpleFileVisitor<Path> {
    private final Path productionOutputPath;
    private Path productionOutputAbsolutePath;

    ModuleScanner(String moduleName) {
        productionOutputPath = Path.of("production", moduleName);
    }

    Path getProductionOutputAbsolutePath() {
        return productionOutputAbsolutePath;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        if (dir.endsWith(productionOutputPath)) {
            productionOutputAbsolutePath = dir;
            return FileVisitResult.TERMINATE;
        }
        return FileVisitResult.CONTINUE;
    }
}
