package net.backlogic.persistence.client.handler;

import net.backlogic.persistence.client.PersistenceException;

import java.util.HashMap;
import java.util.Map;

public class MockServiceHandler implements ServiceHandler {
	private String baseUrl;
	private Map<String, String> outputMap = new HashMap<String, String>();
	private String serviceUrl;
	private Object serviceInput;
	
	
	public MockServiceHandler(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	public void setOutput(String serviceUrl, String output){
		outputMap.put(serviceUrl, output);
	}

	public String getServiceUrl() {
		return serviceUrl;
	}


	public Object getServiceInput() {
		return serviceInput;
	}


	@Override
	public Object invoke(String serviceUrl, Object serviceInput, ReturnType returnType, Class<?> elementType) {
		//record inputs
		this.serviceUrl = serviceUrl;
		this.serviceInput = serviceInput;
		
		//output
		String output = outputMap.get(serviceUrl);
		if ( output == null ) {
			throw new PersistenceException(PersistenceException.InputException, "ServiceUnAvaiable", "Serivce: \"" + serviceUrl + "\" is not avaibale.");
		}
		return output;
	}

}
