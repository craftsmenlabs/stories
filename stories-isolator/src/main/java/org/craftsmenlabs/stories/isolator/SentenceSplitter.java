package org.craftsmenlabs.stories.isolator;

import java.io.IOException;
import java.io.InputStream;
import org.craftsmenlabs.stories.api.models.scrumitems.Issue;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

/**
 * The Isolator can map a string to a card
 */
public class SentenceSplitter
{
	private final String SENTENCEMODEL = "en-sent.bin";
	private SentenceDetectorME sentenceDetector;

	public SentenceSplitter()
	{
		loadSentenceDetector();
	}

	public void loadSentenceDetector()
	{
		InputStream modelIn = null;

		modelIn = SentenceSplitter.class.getClassLoader().getResourceAsStream(SENTENCEMODEL);
		try
		{
			SentenceModel model = new SentenceModel(modelIn);
			sentenceDetector = new SentenceDetectorME(model);

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (modelIn != null)
			{
				try
				{
					modelIn.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public Issue splitSentence(String input)
	{
        Issue issue = new Issue();

	    if(input == null || input.length() == 0){
	        return issue;
        }else {
            final String[] sentences = sentenceDetector.sentDetect(input);

            //currently:
            // - first sentence => user story
            // - second sentence => acceptance criteria
            // by convention
            if(sentences.length > 0) {
                issue.setUserstory(sentences[0]);
            }else{
				issue.setUserstory("");
			}
            if(sentences.length > 1){
                issue.setAcceptanceCriteria(sentences[1]);
            }else{
				issue.setAcceptanceCriteria("");
			}

            return issue;
        }
	}
}