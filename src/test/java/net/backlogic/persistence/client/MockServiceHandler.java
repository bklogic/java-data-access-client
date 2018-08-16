package net.backlogic.persistence.client;

import java.util.HashMap;
import java.util.Map;

import net.backlogic.persistence.client.handler.ServiceHandler;

public class MockServiceHandler implements ServiceHandler {
	private Map<String, String> outputMap;
	private String serviceUrl;
	private String serviceInput;
	private String groupId;
	
	
	public void setOutputMap(Map<String, String> outputMap){
		this.outputMap = outputMap;
	}

	public String getServiceUrl() {
		return serviceUrl;
	}


	public String getServiceInput() {
		return serviceInput;
	}


	public String getGroupId() {
		return groupId;
	}


	@Override
	public String invoke(String serviceUrl, String serviceInput, String groupId) {
		//record inputs
		this.serviceUrl = serviceUrl;
		this.serviceInput = serviceInput;
		this.groupId = groupId;
		
		//output
		String output = outputMap.get(serviceUrl.toLowerCase());
		if ( output == null ) {
			throw new PersistenceException(PersistenceException.InputException, "ServiceUnAvaiable", "Serivce: \"" + serviceUrl + "\" is not avaibale.");
		}
		return output;
	}

	
}
