package org.craftsmenlabs.stories.isolator;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import opennlp.tools.sentdetect.SentenceDetectorME;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SentenceSplitterTest{

	@Mocked SentenceDetectorME sentenceDetector;

	@Tested
	SentenceSplitter sentenceSplitter;

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
