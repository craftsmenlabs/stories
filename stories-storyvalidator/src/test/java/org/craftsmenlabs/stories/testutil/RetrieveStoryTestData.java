package org.craftsmenlabs.stories.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.craftsmenlabs.stories.api.models.scrumitems.Feature;

public class RetrieveStoryTestData
{
	public List<Feature> getTestDataFromResource()
	{

		List<String> testItems = new ArrayList<>();
		testItems.add(new String(""
			+ "As a super office user \n"
			+ "I would like to be informed about the alarms in my user \n"
			+ "so I can have the most preferred alarm on top. \n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about thsdfsdfe total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "sdfs a marketing manager \n"
			+ "I would like to be informed about the total amount osdfsdf alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about thesdfsdf total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about the total amount ofsdf alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed aboutsdf the total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "Asfsdfs a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about the total amsdfount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "Insert namesst preferred alarm on top. \n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed aboutsdf the total amount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "Asfsdfs a marketing manager \n"
			+ "I would like to be informed about the total amount of alarms in my userbase\n"
		));
		testItems.add(new String(""
			+ "As a marketing manager \n"
			+ "I would like to be informed about the total amsdfount of alarms in my userbase\n"
			+ "so I can keep track of. \n"
		));
		testItems.add(new String(""
			+ "Insert namesst preferred alarm on top. \n"
		));

		return testItems.stream().map(s -> Feature.builder().userstory(s).build()).collect(Collectors.toList());
	}
}
