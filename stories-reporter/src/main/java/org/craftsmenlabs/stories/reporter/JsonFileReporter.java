package org.craftsmenlabs.stories.reporter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.Reporter;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;

import java.io.File;
import java.io.IOException;

public class JsonFileReporter implements Reporter {
    private File output;

    public JsonFileReporter(File output) {
        this.output = output;
    }

    public void report(StoriesRun storiesRun){
        ValidatedBacklog backlog = storiesRun.getValidatedBacklog();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(output, backlog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
