package net.backlogic.persistent.client;

import java.util.HashMap;

import net.backlogic.persistence.client.ServiceHandler;

public class HttpHanlderMock implements ServiceHandler {
	
	/*
	 * base url
	 */
	private String baseUrl;
	
	/*
	 * Constructors
	 */
	public HttpHanlderMock() {
	}
	public HttpHanlderMock(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
		
	/*
	 * Invoke persistence service. Take a JSON input and return a JSON output.
	 */
	public String invoke(String serviceUrl, String serviceInput, String groupId) {
	
	  //map
	  HashMap<String, String> map = new HashMap<String, String>();
	  map.put("app/mod/Customer/read", "{\"customerId\":1, \"customerName\":\"wang\"}");
	  map.put("app/mod/Customer/create", "{\"customerId\":1, \"customerName\":\"wang\"}");
	  map.put("app/mod/Customer/delete", "{}");
	  
	  
	  //return
	  return map.get(serviceInput);
	
	}
}
