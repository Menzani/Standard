package eu.menzani.io;

import eu.menzani.collection.ResizableArray;
import eu.menzani.struct.FileExtension;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public class SimpleDirectoryStreamFilter implements DirectoryStream.Filter<Path> {
    private final FileExtension[] fileExtensions;
    private final String[] mustStartWith;
    private final String[] mustEndWith;

    SimpleDirectoryStreamFilter(Builder builder) {
        fileExtensions = builder.fileExtensions.asFixedArray();
        mustStartWith = builder.mustStartWith.asFixedArrayOrNull();
        mustEndWith = builder.mustEndWith.asFixedArrayOrNull();
    }

    @Override
    public boolean accept(Path entry) {
        boolean checkStartsWith = mustStartWith != null;
        boolean checkEndsWith = mustEndWith != null;
        if (checkStartsWith || checkEndsWith) {
            String fileName = entry.getFileName().toString();
            if (checkStartsWith && checkStartsWith(fileName)) {
                return false;
            }
            if (checkEndsWith && checkEndsWith(fileName)) {
                return false;
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

    private boolean checkEndsWith(String fileName) {
        fileName = FileExtension.getFileNameWithoutExtension(fileName);
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

        public SimpleDirectoryStreamFilter build() {
            return new SimpleDirectoryStreamFilter(this);
        }
    }
}
