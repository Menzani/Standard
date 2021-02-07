package eu.menzani.struct;

import java.net.URL;
import java.nio.file.Path;

public class Paths {
    public static boolean contains(Path path, Path subPath) {
        return indexOf(path, subPath) != -1;
    }

    public static int indexOf(Path path, Path subPath) {
        for (int i = 0; i < path.getNameCount(); i++) {
            if (path.getName(i).equals(subPath)) {
                return i;
            }
        }
        return -1;
    }

    public static Path replace(Path path, int index, Path replacement) {
        Path before = path.subpath(0, index);
        Path after = path.subpath(index + 1, path.getNameCount());
        return path.getRoot().resolve(before).resolve(replacement).resolve(after);
    }

    public static Path fromURL(URL url) {
        return Path.of(url.getPath().substring(1));
    }
}
