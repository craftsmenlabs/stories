package org.craftsmenlabs.stories.importer;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

/**
 *
 */
public class TrelloAPIImporterTest
{
	private TrelloAPIImporter _trelloAPIImporter;

	@Test(expected = RuntimeException.class)
	public void testImportFailsOnImproperUrl() throws Exception
	{
		_trelloAPIImporter = new TrelloAPIImporter("http://foo.bar", "1", "2", "To Do");
		String dataImport = _trelloAPIImporter.getDataAsString();
		assertThat(dataImport).contains("");
	}
}
