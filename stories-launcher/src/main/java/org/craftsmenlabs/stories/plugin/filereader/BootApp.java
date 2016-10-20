package org.craftsmenlabs.stories.plugin.filereader;

import org.craftsmenlabs.stories.api.models.Rating;
import org.craftsmenlabs.stories.api.models.validatorentry.validatorconfig.ScorerConfigCopy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BootApp
{

	private final Logger logger = LoggerFactory.getLogger(BootApp.class);
	@Autowired
	private ApplicationConfig applicationConfig;
	@Autowired
	private ValidationConfig validationConfig;

	public static void main(String[] args)
	{
		SpringApplication application = new SpringApplication(BootApp.class);
		application.setBannerMode(Banner.Mode.OFF);
		ApplicationContext context = application.run(args);

		BootApp app = context.getBean(BootApp.class);
		int result = app.startApplication(args);

		LoggerFactory.getLogger(BootApp.class).info("Finished stories plugin succes.");
		SpringApplication.exit(context, new ExitCodeGenerator()
		{
			@Override public int getExitCode()
			{
				return result;
			}
		});

	}

	public int startApplication(String[] args)
	{
		logger.info("Starting stories plugin.");

		PluginExecutor pluginExecutor = new PluginExecutor();
		ScorerConfigCopy scorerConfigCopy = validationConfig.clone();

		Rating rating = pluginExecutor.execute(applicationConfig, scorerConfigCopy);
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