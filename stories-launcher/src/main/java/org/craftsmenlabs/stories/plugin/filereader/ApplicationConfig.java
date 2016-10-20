package org.craftsmenlabs.stories.plugin.filereader;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

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
}
