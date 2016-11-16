package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.connectivity.ConnectivityComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ConnectivityComponent.class)
public class BootApp {

    private final static Logger logger = LoggerFactory.getLogger(BootApp.class);

    @Autowired
    private PluginExecutor pluginExecutor;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(BootApp.class);
        application.setBannerMode(Banner.Mode.OFF);
        ApplicationContext context = application.run(args);

        BootApp app = context.getBean(BootApp.class);
        Rating result = app.startApplication();


        // Return appropriate exit code
        System.exit(result == Rating.SUCCESS ? 0 : -1);
    }

    public Rating startApplication() {
        try {
            Rating rating = pluginExecutor.startApplication();
            logger.info("Finished Storynator application with success.");
            return rating;
        } catch (StoriesException e) {
            logger.info(e.getMessage());
            return Rating.FAIL;
        }
    }
}