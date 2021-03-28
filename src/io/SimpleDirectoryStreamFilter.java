package eu.menzani.io;

import eu.menzani.collection.ResizableArray;
import eu.menzani.struct.FileExtension;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class SimpleDirectoryStreamFilter implements DirectoryStream.Filter<Path> {
    private final FileExtension[] fileExtensions;
    private final String[] mustStartWith;
    private final String[] mustEndWith;
    private final boolean excludeDirectories;

    SimpleDirectoryStreamFilter(Builder builder) {
        fileExtensions = builder.fileExtensions.asFixedArray();
        mustStartWith = builder.mustStartWith.asFixedArrayOrNull();
        mustEndWith = builder.mustEndWith.asFixedArrayOrNull();
        excludeDirectories = builder.excludeDirectories;
    }

    @Override
    public boolean accept(Path entry) {
        Boolean isDirectory = null;
        if (excludeDirectories) {
            isDirectory = Files.isDirectory(entry);
            if (isDirectory) {
                return false;
            }
        }
        boolean checkStartsWith = mustStartWith != null;
        boolean checkEndsWith = mustEndWith != null;
        if (checkStartsWith || checkEndsWith) {
            String fileName = entry.getFileName().toString();
            if (checkStartsWith && checkStartsWith(fileName)) {
                return false;
            }
            if (checkEndsWith) {
                if (isDirectory == null) {
                    isDirectory = Files.isDirectory(entry);
                }
                if (checkEndsWith(fileName, isDirectory)) {
                    return false;
                }
            }
        }
        return FileExtension.endsWith(entry, fileExtensions);
    }

    private boolean checkStartsWith(String fileName) {
        for (String startsWith : mustStartWith) {
            if (fileName.startsWith(startsWith)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkEndsWith(String fileName, boolean isDirectory) {
        if (!isDirectory) {
            fileName = FileExtension.getFileNameWithoutExtension(fileName);
        }
        for (String endsWith : mustEndWith) {
            if (fileName.endsWith(endsWith)) {
                return false;
            }
        }
        return true;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        final ResizableArray<FileExtension> fileExtensions = new ResizableArray<>(FileExtension.class);
        final ResizableArray<String> mustStartWith = new ResizableArray<>(String.class);
        final ResizableArray<String> mustEndWith = new ResizableArray<>(String.class);
        boolean excludeDirectories;

        public Builder orHasExtension(FileExtension fileExtension) {
            fileExtensions.add(fileExtension);
            return this;
        }

        public Builder orMustStartWith(String prefix) {
            mustStartWith.add(prefix);
            return this;
        }

        public Builder orMustEndWith(String suffix) {
            mustEndWith.add(suffix);
            return this;
        }

        public Builder excludeDirectories() {
            excludeDirectories = true;
            return this;
        }

        public SimpleDirectoryStreamFilter build() {
            return new SimpleDirectoryStreamFilter(this);
        }
    }
}
