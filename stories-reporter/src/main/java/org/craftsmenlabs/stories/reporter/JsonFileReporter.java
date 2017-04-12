package org.craftsmenlabs.stories.reporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesReport;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;

import java.io.File;
import java.io.IOException;

public class JsonFileReporter implements Reporter {
    private File output;

    public JsonFileReporter(File output) {
        this.output = output;
    }

    public void report(StoriesReport storiesReport) {
        ValidatedBacklog backlog = storiesReport.getValidatedBacklog();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(output, backlog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
