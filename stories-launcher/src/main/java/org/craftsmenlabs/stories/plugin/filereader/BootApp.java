package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorconfig.ValidationConfigCopy;
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
public class BootApp
{

	private final Logger logger = LoggerFactory.getLogger(BootApp.class);
	@Autowired
	private ApplicationConfig applicationConfig;
	@Autowired
	private ValidationConfig validationConfig;
	@Autowired
	private PluginExecutor pluginExecutor;

	public static void main(String[] args)
	{
		SpringApplication application = new SpringApplication(BootApp.class);
		application.setBannerMode(Banner.Mode.OFF);
		ApplicationContext context = application.run(args);

		BootApp app = context.getBean(BootApp.class);
		int result = app.startApplication(args);

		LoggerFactory.getLogger(BootApp.class).info("Finished Storynator application with succes.");
		if (result == 0)
		{
			System.exit(0);
		}
		else
		{
			System.exit(-1);
		}
	}

	public int startApplication(String[] args)
	{
		logger.info("Starting stories plugin.");

        ValidationConfigCopy validationConfigCopy = validationConfig.clone();

        Rating rating = pluginExecutor.execute(applicationConfig, validationConfigCopy);
        if (rating == Rating.SUCCES)
		{
			return 0;
		}
		else
		{
			return -1;
		}
	}
}