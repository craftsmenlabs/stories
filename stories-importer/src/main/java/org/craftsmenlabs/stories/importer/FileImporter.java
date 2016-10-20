package org.craftsmenlabs.stories.importer;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import org.apache.commons.io.FileUtils;

public class FileImporter implements Importer{
    private String filename;

    public FileImporter(String filename) {
        this.filename = filename;
    }

    public String getDataAsString() {
        String input = null;
        try {
            input = FileUtils.readFileToString(new File(filename), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }


}
