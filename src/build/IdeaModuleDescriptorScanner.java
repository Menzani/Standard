package eu.menzani.build;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class IdeaModuleDescriptorScanner extends DefaultHandler {
    private final List<JarSource> sourceFolders = new ArrayList<>();

    List<JarSource> getSourceFolders(Path modulePath, String moduleName) {
        for (JarSource sourceFolder : sourceFolders) {
            sourceFolder.computePathAndPackagePrefix(modulePath, moduleName);
        }
        return Collections.unmodifiableList(sourceFolders);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("sourceFolder") &&
                "false".equals(attributes.getValue("isTestSource")) && attributes.getValue("type") == null) {
            String url = attributes.getValue("url");
            String name = url.substring(url.lastIndexOf('/') + 1);
            sourceFolders.add(new JarSource(name, attributes.getValue("packagePrefix")));
        }
    }
}
