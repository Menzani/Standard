package eu.menzani.struct;

import eu.menzani.lang.UncaughtException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Arrays;

public class DigestFile {
    private final MessageDigest md5 = newMD5Digest();
    private final Path file;

    public DigestFile(Path folder, String name) {
        this(folder.resolve(name + FileExtension.MD5.getSuffixWithDot()));
    }

    public DigestFile(Path file) {
        this.file = file;
    }

    private MessageDigest newMD5Digest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new UncaughtException(e);
        }
    }

    public void addFiles(Collection<Path> files) throws IOException {
        if (!(files instanceof SortedSet<?>)) {
            List<Path> sorted = new ArrayList<>(files);
            sorted.sort(null);
            files = sorted;
        }

        for (Path file : files) {
            addFile(file);
        }
    }

    public void addFile(Path file) throws IOException {
        md5.update(Files.readAllBytes(file));
    }

    public byte[] save() throws IOException {
        byte[] digest = md5.digest();
        Files.write(file, digest);
        return digest;
    }

    public boolean wereChanged() throws IOException {
        byte[] digest = save();
        if (Files.exists(file)) {
            byte[] oldDigest = Files.readAllBytes(file);
            return !Arrays.equals(oldDigest, digest);
        }
        return true;
    }
}
