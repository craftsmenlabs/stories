package org.craftsmenlabs.stories.reporter;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;

import java.io.File;
import java.io.IOException;

public class JsonFileReporter implements Reporter{
    File output;

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
