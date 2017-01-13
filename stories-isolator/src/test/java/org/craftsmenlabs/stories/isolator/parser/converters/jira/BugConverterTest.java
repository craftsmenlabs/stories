package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import mockit.Expectations;
import mockit.Mocked;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.items.Bug;
import org.craftsmenlabs.stories.isolator.model.jira.Fields;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.model.jira.Priority;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class BugConverterTest {

    @Mocked
    FieldMappingConfig config;

    @Mocked
    SourceConfig sourceConfig;

    @Test
    public void testConvertWithNonCustomFields() throws Exception {
        Map<String, Object> fieldMap = new HashMap<String, Object>(){{
            put("rank", "1" );
            put("summary", "summary" );
            put("description", "description" );
            put("priority", new Priority() );
            put("custom_field1502", "AcceptationCriteria");
            put("custom_field1503", "ExpectedBehavior" );
            put("environment", "Software" );
            put("custom_field1504", "ReproductionPath" );
        }};

        Fields fields = new Fields();
        fields.setAdditionalProperties(fieldMap);

        JiraJsonIssue jiraJsonIssue = JiraJsonIssue.builder()
                .key("key")
                .fields(fields)
                .build();

        Bug expected = Bug.builder()
                .key("key")
                .rank("1")
                .summary("summary")
                .description("description")
                .acceptationCriteria("AcceptationCriteria")
                .expectedBehavior("ExpectedBehavior")
                .software("Software")
                .reproductionPath("ReproductionPath")
                .externalURI("/projects/projectKey/issues/key")
                .build();

        new Expectations(){{
           config.getRank();
           result = "rank";

            config.getBug().getAcceptationCriteria();
            result = "custom_field1502";

            config.getBug().getExpectedBehavior();
            result = "custom_field1503";

            config.getBug().getSoftware();
            result = "environment";

            config.getBug().getReproductionPath();
            result = "custom_field1504";

            sourceConfig.getJira().getUrl();
            result = "";

            sourceConfig.getJira().getProjectKey();
            result = "projectKey";
        }};


        BugConverter bugConverter = new BugConverter(config, sourceConfig);

        assertThat(bugConverter.convert(jiraJsonIssue)).isEqualTo(expected);
    }

    @Test
    public void getSupportedTypes() throws Exception {
    }

}