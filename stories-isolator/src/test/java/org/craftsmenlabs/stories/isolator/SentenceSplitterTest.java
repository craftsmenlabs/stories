package org.craftsmenlabs.stories.isolator;

import static org.assertj.core.api.Assertions.assertThat;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.junit.Test;
import mockit.*;
import opennlp.tools.sentdetect.SentenceDetectorME;

public class SentenceSplitterTest{

	@Mocked
	private SentenceDetectorME sentenceDetector;

	@Tested
	private SentenceSplitter sentenceSplitter;

	@Test
	public void splitSentenceTest() throws Exception
	{
		String input = "As a. Given that";
		new Expectations()
		{{
			sentenceDetector.sentDetect(anyString);
			result = new String[] { "As a", "Given that" };
		}};

		Issue issue = sentenceSplitter.splitSentence(input);
		assertThat(issue.getUserstory()).isEqualTo("As a");
		assertThat(issue.getAcceptanceCriteria()).isEqualTo("Given that");
	}
}
