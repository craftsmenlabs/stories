package org.craftsmenlabs.stories.importer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 */
public class JiraAPIImporterTest
{
//	@Tested
	JiraAPIImporter _jiraAPIImporter;

	@Test
	public void testimportFrom() throws Exception
	{
		_jiraAPIImporter = new JiraAPIImporter("http://foo.bar", "1", "2", "To Do");
		String dataImport = _jiraAPIImporter.getDataAsString();
		assertThat(dataImport).contains("");
	}
}
