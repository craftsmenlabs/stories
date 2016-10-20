package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BootApp {

    @Autowired
    private ApplicationConfig applicationConfig;

    @Autowired
    private ValidationConfig validationConfig;

    private final Logger logger = LoggerFactory.getLogger(BootApp.class);

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BootApp.class, args);

        BootApp app = context.getBean(BootApp.class);
        app.startApplication(args);
    }

    public void startApplication(String[] args) {
        logger.info("Starting stories plugin.");

        PluginExecutor pluginExecutor = new PluginExecutor();
        ScorerConfigCopy scorerConfigCopy = validationConfig.clone();

        Rating rating = pluginExecutor.execute(applicationConfig, scorerConfigCopy);
        if (rating == Rating.SUCCES)
        {
            System.exit(0);
        }
        else
        {
            System.exit(-1);
        }

        logger.info("Finished stories plugin.");
    }
}