/**
 * 
 */
package net.backlogic.persistence.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Ken
 * Created on 10/23/2017
 * 
 * Responsible for handling HTTP service request
 */
public class ServiceHandlerImpl implements ServiceHandler {
	/*
	 * base url
	 */
	private String baseUrl;
	
	/*
	 * Constructors
	 */
	public ServiceHandlerImpl() {
	}
	public ServiceHandlerImpl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	
	/*
	 * Invoke persistence service. Take a JSON input and return a JSON output.
	 */
	public String invoke(String serviceUrl, String serviceInput, String groupId) {
		  String output = "";	
		  try {

				URL url = new URL(baseUrl + serviceUrl);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				OutputStream os = conn.getOutputStream();
				os.write(serviceInput.getBytes());
				os.flush();

				if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
					throw new PersistenceException(PersistenceException.HttpException, "HttpError", "Failed : HTTP error code : "
							+ conn.getResponseCode() );						  
				}

				//read service output
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				String line;
				while ((line = br.readLine()) != null) {
					output += line;
				}

				conn.disconnect();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }
		
		//return
		return output;
		
	}
	
	
}
