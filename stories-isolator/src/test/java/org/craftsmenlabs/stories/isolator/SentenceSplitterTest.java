package org.craftsmenlabs.stories.isolator;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Tested;
import opennlp.tools.sentdetect.SentenceDetectorME;
import org.craftsmenlabs.stories.api.models.items.base.Feature;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

		Feature feature = sentenceSplitter.splitSentence(new Feature(), input);
		assertThat(feature.getUserstory()).isEqualTo("As a");
		assertThat(feature.getAcceptanceCriteria()).isEqualTo("Given that");
	}
}
