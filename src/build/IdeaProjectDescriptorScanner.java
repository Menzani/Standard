package eu.menzani.build;

import eu.menzani.lang.EndVisit;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.nio.file.Path;

class IdeaProjectDescriptorScanner extends DefaultHandler {
    private Path outputDirectory;

    Path getOutputDirectory() {
        return outputDirectory;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("output")) {
            outputDirectory = Path.of(attributes.getValue("url").substring(7));
            throw EndVisit.INSTANCE;
        }
    }
}
