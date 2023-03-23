package net.backlogic.persistence.client;

import java.util.function.Supplier;

import net.backlogic.persistence.client.auth.DevTimeCredential;
import net.backlogic.persistence.client.auth.DevTimeJwtProvider;
import net.backlogic.persistence.client.proxy.UrlUtil;

public class DataAccessClientBuilder {
	private String baseUrl;
	private DevTimeCredential credential;
	private Supplier<String> jwtProvider;
	private boolean logRequest;
	
	public DataAccessClientBuilder() {
		this.jwtProvider = () -> "";
		this.logRequest = false;
	}
	
	public DataAccessClientBuilder baseUrl(String baseUrl) {
		this.baseUrl = UrlUtil.formatBaseUrl(baseUrl);
		return this;
	}
		
	public DataAccessClientBuilder jwtProvider(Supplier<String> jwtProvider) {
		if (jwtProvider != null) {
			this.jwtProvider = jwtProvider;
		}
		return this;			
	}
		
	public DataAccessClientBuilder devTimeCredential(DevTimeCredential credential) {
		this.credential = credential;
		return this;
	}
	
	public DataAccessClientBuilder logRequest(boolean logRequest) {
		this.logRequest = logRequest;
		return this;
	}
	
	public DataAccessClient build() {
		DataAccessClient client = new DataAccessClient(this.baseUrl);
		client.setJwtProvider( (credential == null) ? this.jwtProvider : new DevTimeJwtProvider(this.credential) );
		client.logRequest(this.logRequest);
		return client;
	}

}
