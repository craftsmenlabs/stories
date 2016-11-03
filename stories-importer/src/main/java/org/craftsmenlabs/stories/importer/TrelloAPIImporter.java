package org.craftsmenlabs.stories.importer;

import java.io.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Importer
 */
public class TrelloAPIImporter implements Importer
{
	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int DOWNLOAD_TIMEOUT = 60000;

	private final Logger logger = LoggerFactory.getLogger(TrelloAPIImporter.class);

	private HttpURLConnection conn;

	private String urlResource;
	private String projectKey;
	private String authKey;
	private String token;

	public TrelloAPIImporter(String urlResource, String projectKey, String authKey, String token)
	{
		this.urlResource = urlResource;
		this.projectKey = projectKey;
		this.authKey = authKey;
		this.token = token;
	}

	public String getDataAsString()
	{
		String returnsResponse = "";

		try
		{
			URL url = new URL(urlResource
				+ "/boards/" + httpEncode(projectKey) + "/cards?"
				+ "key=" + httpEncode(authKey) + "&"
				+ "token=" + httpEncode(token));

			logger.info("Retrieving data from:" + url.toString());

			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(CONNECTION_TIMEOUT);
			conn.setReadTimeout(DOWNLOAD_TIMEOUT);

			//execute call
			if (conn.getResponseCode() != 200)
			{
				logger.error("Failed to connect to "
					+ url
					+ ". Connection returned HTTP code: "
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
			logger.error("Failed to connect to create a proper connection URL with parameters:" + getParameters());
			abortOnError();
		}

		return returnsResponse;
	}

	private void abortOnError()
	{
		throw new RuntimeException("Failed to connect to " + urlResource);
	}

	private String httpEncode(String toEncode) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(toEncode, "UTF-8");
	}

	private String getParameters()
	{
		return "URL" + urlResource
			+ " AUTH:" + authKey
			+ " PROJECT:" + projectKey
			+ " TOKEN:" + token;
	}
}
