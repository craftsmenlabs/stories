package org.craftsmenlabs.stories.launcher;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.exception.StoriesException;
import org.craftsmenlabs.stories.api.models.items.validated.ValidatedBacklog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class BootApp {

    private final static Logger logger = LoggerFactory.getLogger(BootApp.class);

    @Autowired
    private StorynatorStandaloneExecutor storynatorStandaloneExecutor;

    public static void main(String[] args) throws IOException {
        SpringApplication application = new SpringApplication(BootApp.class);
        application.setBannerMode(Banner.Mode.OFF);

        // Load in default properties
        Properties defaults = new Properties();
        defaults.load(BootApp.class.getClassLoader().getResourceAsStream("application-default.properties"));
        application.setDefaultProperties(defaults);

        ApplicationContext context = application.run(args);

        BootApp app = context.getBean(BootApp.class);
        Rating result = app.startApplication();


        // Return appropriate exit code
        System.exit(result == Rating.SUCCESS ? 0 : -1);
    }

    public Rating startApplication() {
        try {
            ValidatedBacklog validatedBacklog = storynatorStandaloneExecutor.runApplication();
            logger.info("Finished Storynator application with success.");
            return validatedBacklog.getRating();
        } catch (StoriesException e) {
            logger.info(e.getMessage());
            return Rating.FAIL;
        }
    }
}