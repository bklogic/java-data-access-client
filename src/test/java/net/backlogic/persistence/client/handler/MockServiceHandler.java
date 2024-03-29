package net.backlogic.persistence.client.handler;

import net.backlogic.persistence.client.auth.JwtProvider;

import java.util.HashMap;
import java.util.Map;

public class MockServiceHandler implements ServiceHandler {
	private String baseUrl;
	private Map<String, String> outputMap = new HashMap<String, String>();
	private Map<String, Object> inputStore = new HashMap<>();
	private Map<String, Object> outputStore = new HashMap<>();

	public MockServiceHandler(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setOutput(String serviceUrl, String output){
		outputMap.put(serviceUrl, output);
	}

	public void addOutput(String serviceUrl, Object output) {
		this.outputStore.put(serviceUrl, output);
	}

	public Object getInput(String serviceUrl) {
		return this.inputStore.get(serviceUrl);
	}

	@Override
	public Object invoke(String serviceUrl, Object serviceInput, ReturnType returnType, Class<?> elementType, boolean retryOn403) {
		// save input
		String url = baseUrl + serviceUrl;
		this.inputStore.put(url, serviceInput);

		// return output
		return this.outputStore.get(url);
	}

	@Override
	public void setJwtProvider(JwtProvider jwtProvider) {
	}

	@Override
	public void logRequest(boolean logRequest) {
	}

}
