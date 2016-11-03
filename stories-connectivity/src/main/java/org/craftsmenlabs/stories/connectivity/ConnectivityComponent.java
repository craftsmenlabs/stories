package org.craftsmenlabs.stories.connectivity;

import org.craftsmenlabs.stories.connectivity.service.community.CommunityConnectivityServicesss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ComponentScan
public class ConnectivityComponent
{
	@Autowired
	CommunityConnectivityServicesss communityConnectivityServices;
}
