package org.craftsmenlabs.stories.importer;

import java.io.*;
import java.net.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Importer
 */
public class JiraAPIImporter implements Importer
{
	static final URL SOURCE_PATHS = JiraAPIImporter.class.getResource("logback.xml");

	public static final int CONNECTION_TIMEOUT = 5000;
	public static final int DOWNLOAD_TIMEOUT = 60000;

	private final Logger logger = LoggerFactory.getLogger(JiraAPIImporter.class);

	private HttpURLConnection conn;

	private String urlResource;
	private String projectKey;
	private String authKey;
	private String statusKey;

    public JiraAPIImporter(String urlResource, String projectKey, String authKey, String statusKey) {
        this.urlResource = urlResource;
        this.projectKey = projectKey;
        this.authKey = authKey;
        this.statusKey = statusKey;
    }

    public String getDataAsString()
	{
		String returnsResponse = "";
		try
		{
			URL url = new URL(urlResource
				+ "/rest/api/2/search?jql="
				+ "project%3D" + httpEncode(projectKey) + "+AND+"
				+ "type%3DStory+AND+"
				+ "status%3D\"" + httpEncode(statusKey) + "\""
				+ "&maxResults=100000");
			logger.info("Retrieving data form:" + url.toString());

			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Authorization", "Basic " + authKey);
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

				throw new RuntimeException("Failed (HTTP: "
					+ conn.getResponseCode()
					+ ") to retrieve the data from the URL:"
					+ url);
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
		catch (MalformedURLException e)
		{

			e.printStackTrace();

		}
		catch (IOException e)
		{

			e.printStackTrace();

		}
		//writeToFile("tmp.json",returnsResponse.toString());

		return returnsResponse;
	}

	public void writeToFile(String filename, String data)
	{
		File f = new File("tmp.json");
		try (FileOutputStream out = new FileOutputStream(filename))
		{
			out.write(data.getBytes());
			out.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private String httpEncode(String toEncode) throws UnsupportedEncodingException
	{
		return URLEncoder.encode(toEncode, "UTF-8");
	}
}
