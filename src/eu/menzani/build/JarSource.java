package eu.menzani.build;

import eu.menzani.lang.Optional;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

class JarSource {
    private final String name;
    private Path path;
    private String packagePrefix;

    static List<JarSource> single(Path path) {
        return Collections.singletonList(new JarSource(path));
    }

    private JarSource(Path path) {
        name = null;
        this.path = path;
        packagePrefix = "";
    }

    JarSource(String name, @Optional String packagePrefix) {
        this.name = name;
        this.packagePrefix = packagePrefix;
    }

    void computePathAndPackagePrefix(Path modulePath, String moduleName) {
        assert name != null;
        path = modulePath.resolve(name);

        if (packagePrefix == null) {
            System.out.println("Module source folder has no package prefix: " + moduleName + '/' + name);
            packagePrefix = "";
        } else {
            packagePrefix = packagePrefix.replace('.', '/') + '/';
        }
    }

    Path getPath() {
        return path;
    }

    String getPackagePrefix() {
        return packagePrefix;
    }
}
