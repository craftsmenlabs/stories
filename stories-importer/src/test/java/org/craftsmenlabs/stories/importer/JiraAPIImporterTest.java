package org.craftsmenlabs.stories.importer;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 */
public class JiraAPIImporterTest
{
	private JiraAPIImporter jiraAPIImporter;

	@Test(expected = RuntimeException.class)
	public void testImportFailsOnImproperUrl() throws Exception
	{
		jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "2", "To Do");
		String dataImport = jiraAPIImporter.getDataAsString();
		assertThat(dataImport).contains("");
	}
}
