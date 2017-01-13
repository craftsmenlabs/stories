package org.craftsmenlabs.stories.isolator;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.craftsmenlabs.stories.api.models.items.Feature;

import java.io.IOException;
import java.io.InputStream;

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

	public Feature splitSentence(Feature feature, String input)
	{
	    if(input == null || input.length() == 0){
	        return feature;
        }else {
            final String[] sentences = sentenceDetector.sentDetect(input);

            //currently:
            // - first sentence => user story
            // - second sentence => acceptance criteria
            // by convention
            if(sentences.length > 0) {
                feature.setUserstory(sentences[0]);
            }else{
				feature.setUserstory("");
			}
            if(sentences.length > 1){
                feature.setAcceptanceCriteria(sentences[1]);
            }else{
				feature.setAcceptanceCriteria("");
			}

            return feature;
        }
	}
}