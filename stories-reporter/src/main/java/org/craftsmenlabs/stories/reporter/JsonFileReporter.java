package org.craftsmenlabs.stories.reporter;

import java.io.File;
import java.io.IOException;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileReporter implements Reporter{
    private File output;

    public JsonFileReporter(File output) {
        this.output = output;
    }

    public void report(BacklogValidatorEntry backlog){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(output, backlog);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
