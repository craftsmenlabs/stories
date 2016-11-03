package org.craftsmenlabs.stories.connectivity.service.community;

import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 */
@Profile("!enterprise")
@Component
public class CommunityConnectivityServicesss implements ConnectivityService
{

	private final Logger logger = LoggerFactory.getLogger(CommunityConnectivityServicesss.class);

	@Override public void sendData(StoriesRun storiesRun)
	{
		logger.info("\n"
			+ "***\n"
			+ "Thank you for using the community version.");
		logger.info(
			"In order to use the data export service. Please look at our enterprise version. \nContact us at: http://craftsmenlabs"
				+ ".nl/.\n***\n");
	}
}
