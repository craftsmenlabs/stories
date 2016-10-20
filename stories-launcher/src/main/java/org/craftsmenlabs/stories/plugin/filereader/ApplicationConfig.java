package org.craftsmenlabs.stories.plugin.filereader;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties("application")
public class ApplicationConfig {
    private String dataformat;
    private String inputfile;
    private String outputfile;
    private String url;
    private String authkey;
    private String projectkey;
    private String status;
}
