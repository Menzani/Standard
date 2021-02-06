package eu.menzani.io;

import eu.menzani.lang.UncaughtException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class IOStreams {
    public static String toString(InputStream stream) {
        return toString(stream, StandardCharsets.UTF_8);
    }

    public static String toString(InputStream stream, Charset charset) {
        try (stream) {
            return new String(stream.readAllBytes(), charset);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }

    public static BufferedImage toImage(InputStream stream) {
        try (stream) {
            return ImageIO.read(stream);
        } catch (IOException e) {
            throw new UncaughtException(e);
        }
    }
}
