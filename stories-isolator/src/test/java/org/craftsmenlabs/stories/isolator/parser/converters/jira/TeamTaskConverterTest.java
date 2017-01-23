package org.craftsmenlabs.stories.isolator.parser.converters.jira;

import mockit.Expectations;
import mockit.Mocked;
import org.craftsmenlabs.stories.api.models.config.FieldMappingConfig;
import org.craftsmenlabs.stories.api.models.config.SourceConfig;
import org.craftsmenlabs.stories.api.models.logging.StandaloneLogger;
import org.craftsmenlabs.stories.api.models.scrumitems.TeamTask;
import org.craftsmenlabs.stories.isolator.model.jira.Fields;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.craftsmenlabs.stories.isolator.model.jira.Priority;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamTaskConverterTest {

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
            put("custom_field1503", "estimation" );
            put("priority", new Priority() );
            put("custom_field1502", "AcceptationCriteria");
        }};

        Fields fields = new Fields();
        fields.setAdditionalProperties(fieldMap);

        JiraJsonIssue jiraJsonIssue = JiraJsonIssue.builder()
                .key("key")
                .fields(fields)
                .build();

        TeamTask expected = TeamTask.builder()
                .key("key")
                .rank("1")
                .summary("summary")
                .description("description")
                .acceptationCriteria("AcceptationCriteria")
                .estimation(0f)
                .externalURI("/projects/projectKey/issues/key")
                .build();

        new Expectations(){{
           config.getRank();
           result = "rank";

            config.getTeamTask().getAcceptanceCriteria();
            result = "custom_field1502";

            config.getTeamTask().getEstimation();
            result = "custom_field1503";

            sourceConfig.getJira().getUrl();
            result = "";

            sourceConfig.getJira().getProjectKey();
            result = "projectKey";
        }};


        TeamTaskConverter teamTaskConverter = new TeamTaskConverter(new StandaloneLogger(), config, sourceConfig);

        assertThat(teamTaskConverter.convert(jiraJsonIssue)).isEqualTo(expected);
    }


}