/**
 * 
 */
package net.backlogic.persistence.client.proxy;

import java.util.HashMap;

import net.backlogic.persistence.client.handler.JsonHandler;
import net.backlogic.persistence.client.handler.ServiceHandler;

/**
 * @author Ken
 * Created on 10/23/2017
 * 
 * Implementation of ServiceGroup interface
 */
public class ServiceGroupImpl implements ServiceGroup {
	//HTTP service handler
	private ServiceHandler serviceHandler;
	//JSON handler
	private JsonHandler jsonHandler;
	
	public ServiceGroupImpl (ServiceHandler serviceHandler, JsonHandler jsonHandler) {
		this.serviceHandler = serviceHandler;
		this.jsonHandler = jsonHandler;
	}
	
	public String create() {
		//service url and input
		String url = "generic/group/create";
		String input = "{}";
		
		//call service handler
		String outputJson = serviceHandler.invoke(url, input, null);
		@SuppressWarnings("unchecked")
		HashMap<String, String> output = (HashMap<String, String>)jsonHandler.toObject(outputJson, HashMap.class);
		
		//extract group id
		String groupId = output.get("groupId");
		
		return groupId;
	}


	public void commit(String groupId) {
		//service url and input
		String url = "generic/group/commit";
		String input = "{ \"groupId:\" \"" + groupId + "\" }";
		
		//call service handler
		String output = serviceHandler.invoke(url, input, null);
		
		//check exception
	}

	
	public void cancel(String groupId) {
		//service url and input
		String url = "generic/group/cancel";
		String input = "{ \"groupId:\" \"" + groupId + "\" }";
		
		//call service handler
		String output = serviceHandler.invoke(url, input, null);
		
		//check exception
	}

}
