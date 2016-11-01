package org.craftsmenlabs.stories.reporter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.craftsmenlabs.stories.api.models.summary.Summary;
import org.craftsmenlabs.stories.api.models.summary.SummaryBuilder;
import org.craftsmenlabs.stories.api.models.validatorentry.BacklogValidatorEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SummaryConsoleReporter implements Reporter {
    private final Logger logger = LoggerFactory.getLogger(SummaryConsoleReporter.class);

    @Override
    public void report(BacklogValidatorEntry backlogValidatorEntry) {
        Summary summary = new SummaryBuilder().build(backlogValidatorEntry);

        logger.info(summary.toString());
    }

    public void reportJson(BacklogValidatorEntry backlogValidatorEntry){
        Summary summary = new SummaryBuilder().build(backlogValidatorEntry);
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            String s = mapper.writeValueAsString(summary);
            logger.info(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
