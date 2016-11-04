package org.craftsmenlabs.stories.connectivity.service.enterprise;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.craftsmenlabs.stories.api.models.StoriesRun;
import org.craftsmenlabs.stories.connectivity.ConnectivityConfiguration;
import org.craftsmenlabs.stories.connectivity.service.ConnectivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 *
 */
@Profile("!community")
@Component
public class DashboardConnectivityService implements ConnectivityService
{
    public static final int CONNECTION_TIMEOUT = 5000;
    private final Logger logger = LoggerFactory.getLogger(DashboardConnectivityService.class);
	@Autowired
	ConnectivityConfiguration connectivityConfiguration;

	public void sendData(StoriesRun storiesRun)
	{
        if (connectivityConfiguration.getServiceUrl() != null
            && connectivityConfiguration.getServiceUrl().length() != 0
            && connectivityConfiguration.getToken() != null
            && connectivityConfiguration.getToken().length() != 0)
        {

            storiesRun.setProjectToken(connectivityConfiguration.getToken());

            HttpURLConnection conn;

            logger.info("Instantiating dashboard data submitter.");
            logger.info("Authkey:" + connectivityConfiguration.getToken());
            logger.info("Dashboard url:" + connectivityConfiguration.getServiceUrl());

            String returnsResponse = "";
            try
            {
                URL url = new URL(connectivityConfiguration.getServiceUrl()
                    + "/storiesrun");
                logger.info("Connection to external server on:" + url.toString());

                conn = (HttpURLConnection)url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");

                conn.setConnectTimeout(CONNECTION_TIMEOUT);

                OutputStream os = conn.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));

                String data = mapper.writeValueAsString(storiesRun);

                os.write(data.getBytes());
                os.flush();
                os.close();

                //execute call
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK)
                {
                    logger.error("Failed to connect to "
                        + url
                        + ". StoriesRunPoster returned HTTP code: "
                        + conn.getResponseCode()
                        + "\n"
                        + conn
                        .getResponseMessage());

                    abortOnError();
                }

                // Buffer the result into a string
                BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null)
                {
                    sb.append(line);
                }
                rd.close();

                returnsResponse = sb.toString();

                conn.disconnect();

            }
            catch (IOException e)
            {
                logger.error("Failed to connect: " + e);
                abortOnError();
            }
        }else{
            logger.info("Could not send data to external dashboard. No configuration provided");
            logger.error("Could not send data to external dashboard. No configuration provided");
        }
    }

    private void abortOnError() {
        throw new RuntimeException("Failed to connect to " + connectivityConfiguration.getServiceUrl());
    }

}
