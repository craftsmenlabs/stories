package org.craftsmenlabs.stories.connectivity.service.enterprise;

import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.connectivity.ConnectivityConfiguration;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 *
 */
@Profile("!community")
@Component
public class DashboardConnectivityService implements ConnectivityService
{
	private final Logger logger = LoggerFactory.getLogger(DashboardConnectivityService.class);

	@Autowired
	ConnectivityConfiguration connectivityConfiguration;

	public void sendData(StoriesRun storiesRun)
	{
		logger.info("Instantiating dashboard data submitter.");
		logger.info("Authkey:" + connectivityConfiguration.getAuthKey());
		logger.info("Dashboard url:" + connectivityConfiguration.getServiceUrl());

	}
}
