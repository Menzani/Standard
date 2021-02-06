package eu.menzani.swing;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.StringJoiner;

public class SimpleFileFilter extends FileFilter {
    private final String[] extensions;
    private final String description;

    public SimpleFileFilter(String description, String... extensions) {
        this.extensions = extensions;
        for (int i = 0; i < extensions.length; i++) {
            extensions[i] = '.' + extensions[i];
        }

        StringJoiner joiner = new StringJoiner(", ", description + " (", ")");
        for (String extension : this.extensions) {
            joiner.add('*' + extension);
        }
        this.description = joiner.toString();
    }

    @Override
    public boolean accept(File file) {
        String fileToString = file.toString();
        for (String extension : extensions) {
            if (fileToString.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
