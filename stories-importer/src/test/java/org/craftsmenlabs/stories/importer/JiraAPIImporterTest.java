package org.craftsmenlabs.stories.importer;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 */
public class JiraAPIImporterTest
{
//	@Tested
JiraAPIImporter jiraAPIImporter;

	@Test
	public void testimportFrom() throws Exception
	{
		jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "2", "To Do");
		String dataImport = jiraAPIImporter.getDataAsString();
		assertThat(dataImport).contains("");
	}
}
