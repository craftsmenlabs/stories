package org.craftsmenlabs.stories.importer;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class JiraAPIImporterTest {
    private JiraAPIImporter jiraAPIImporter;

    @Mocked
    private HttpURLConnection connection;

    @Test(expected = RuntimeException.class)
    public void testImportFailsOnImproperUrl() throws Exception {
        jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "2", "To Do");
        String dataImport = jiraAPIImporter.getDataAsString();
        assertThat(dataImport).contains("");
    }


    @Test(expected = RuntimeException.class)
    public void testImportFailsOnWrongStatusCode() throws IOException {
        new Expectations() {{
            connection.getResponseCode();
            result = 400;

            connection.getResponseMessage();
            result = "Bad Request";
        }};

        jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "2", "To Do");
        jiraAPIImporter.getDataAsString();
    }

    @Ignore("Can not get mocking of response to work")
    @Test
    public void testImportSucceedsOn200StatusCode() throws IOException {
        new Expectations() {{
            connection.getResponseCode();
            result = 200;

            connection.getDoInput();
            result = true;

            connection.getInputStream();
            result = new ByteArrayInputStream("{}".getBytes());
        }};

        jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "2", "To Do");
        String result = jiraAPIImporter.getDataAsString();
        assertThat(result).isEqualTo("{}");
    }
}
