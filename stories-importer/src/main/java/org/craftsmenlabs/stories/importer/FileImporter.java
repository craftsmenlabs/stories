package org.craftsmenlabs.stories.importer;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;

public class FileImporter implements Importer{
    String filename;

    public FileImporter(String filename) {
        this.filename = filename;
    }

    public Optional<String> getDataAsString() {
        try {
            return Optional.of(FileUtils.readFileToString(new File(filename), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();

        }
    }
}
