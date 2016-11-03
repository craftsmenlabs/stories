package org.craftsmenlabs.stories.connectivity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("dashboard")
public class ConnectivityConfiguration
{
	private String serviceUrl;
	private String authKey;
}
