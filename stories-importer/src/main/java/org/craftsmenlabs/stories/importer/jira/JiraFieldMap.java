package org.craftsmenlabs.stories.importer.jira;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 *  For parsing the jira field data from for example:
 *  https://jira.codecentric.de/rest/api/2/field
 * {
 *     "id":"customfield_12901",
 *     "name":"Reproduction path",
 *     "custom":true,
 *     "orderable":true,
 *     "navigable":true,
 *     "searchable":true,
 *     "clauseNames":["cf[12901]","Reproduction path"],
 *     "schema":{"type":"string","custom":"com.atlassian.jira.plugin.system.customfieldtypes:textarea",
 *     "customId":12901}
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class JiraFieldMap {
    private String id;
    private String name;
}

