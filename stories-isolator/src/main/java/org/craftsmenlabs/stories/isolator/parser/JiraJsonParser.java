package org.craftsmenlabs.stories.isolator.parser;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.craftsmenlabs.stories.isolator.SentenceSplitter;
import org.craftsmenlabs.stories.isolator.model.jira.JiraBacklog;
import org.craftsmenlabs.stories.isolator.model.jira.JiraJsonIssue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class JiraJsonParser implements Parser {

    private final Logger logger = LoggerFactory.getLogger(JiraJsonParser.class);
    private FieldMappingConfigCopy fieldMapping;

    public JiraJsonParser(FieldMappingConfigCopy fieldMapping) {
        this.fieldMapping = fieldMapping;
    }

    public List<Issue> getIssues(List<JiraJsonIssue> jiraJsonIssues) {
        SentenceSplitter sentenceSplitter = new SentenceSplitter();

        return jiraJsonIssues.stream()
                .map(jiraJsonIssue -> {
                    Issue issue;

                    if (hasNoValidDescription(jiraJsonIssue)) {
                        issue = new Issue();
                    } else {
                        issue = sentenceSplitter.splitSentence(jiraJsonIssue.getFields().getDescription());
                    }

                    issue.setSummary(jiraJsonIssue.getFields().getSummary());
                    issue.setKey(jiraJsonIssue.getKey());
                    issue.setIssueType(jiraJsonIssue.getFields().getIssuetype().getName());

                    Map<String, Object> additionalProps = jiraJsonIssue.getFields().getAdditionalProperties();
                    Map<String, String> stringProps = new HashMap<>();
                    for (Entry<String, Object> entries : additionalProps.entrySet()) {
                        if (entries.getValue() != null) {
                            stringProps.put(entries.getKey(), entries.getValue().toString());
                        } else {
                            stringProps.put(entries.getKey(), "");
                        }
                    }

                    String rank = stringProps.get(fieldMapping.getIssue().getRank());
                    if(rank == null) {
                        throw new StoriesException("The issue rank was not defined. Is the fieldMapping configured correctly?");
                    }
                    issue.setRank(rank);

                    float estimation = 0f;
                    try {
                        if (stringProps.get(fieldMapping.getIssue().getEstimation()) != null) {
                            estimation = Float.parseFloat(stringProps.get(fieldMapping.getIssue().getEstimation()));
                        }
                    } catch (NumberFormatException nfe) {
                        logger.warn("Parsing of estimation to float failed. By default set to 0.0");
                    }
                    issue.setEstimation(estimation);

                    return issue;
                }).collect(Collectors.toList());
    }

    public boolean hasNoValidDescription(JiraJsonIssue jiraJsonIssue) {
        return jiraJsonIssue.getFields().getDescription() == null || jiraJsonIssue.getFields().getDescription().isEmpty();
    }


    public List<Issue> getIssues(String input) {
        return getIssues(getJiraJsonIssues(input));
    }


    public List<JiraJsonIssue> getJiraJsonIssues(String input){
        if (input == null || input.isEmpty())
            return null;

        List<JiraJsonIssue> jiraJsonIssues = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            JiraBacklog jiraBacklog = mapper.readValue(input, JiraBacklog.class);
            jiraJsonIssues = jiraBacklog.getJiraJsonIssues();
        } catch(JsonParseException | JsonMappingException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jiraJsonIssues;
    }
}
