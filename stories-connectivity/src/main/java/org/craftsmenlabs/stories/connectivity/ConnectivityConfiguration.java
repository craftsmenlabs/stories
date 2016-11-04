package org.craftsmenlabs.stories.connectivity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("dashboard")
public class ConnectivityConfiguration
{
	private String serviceUrl;
    private String token;
}
