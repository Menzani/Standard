package eu.menzani.swing;

import eu.menzani.struct.FileExtension;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.StringJoiner;

public class SimpleFileFilter extends FileFilter {
    private final FileExtension[] fileExtensions;
    private final String description;

    public SimpleFileFilter(String description, FileExtension... fileExtensions) {
        this.fileExtensions = fileExtensions;

        StringJoiner joiner = new StringJoiner(", ", description + " (", ")");
        for (FileExtension extension : fileExtensions) {
            joiner.add('*' + extension.getSuffixWithDot());
        }
        this.description = joiner.toString();
    }

    @Override
    public boolean accept(File file) {
        return FileExtension.endsWith(file, fileExtensions);
    }

    @Override
    public String getDescription() {
        return description;
    }
}
