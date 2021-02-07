package eu.menzani.misc;

import eu.menzani.lang.EndVisit;
import eu.menzani.lang.UncaughtException;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class XmlParser {
    private static final SAXParser parser;

    static {
        try {
            parser = SAXParserFactory.newInstance().newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new UncaughtException(e);
        }
    }

    public static void parse(Path file, DefaultHandler visitor) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
            parser.parse(stream, visitor);
        } catch (EndVisit ignored) {
        } catch (SAXException e) {
            throw new UncaughtException(e);
        }
    }
}
