package net.backlogic.persistence.client;

import java.util.function.Supplier;

import net.backlogic.persistence.client.proxy.UrlUtil;

public class DataAccessClientBuilder {
	private String baseUrl;
	private Supplier<String> jwtProvider;
	
	public DataAccessClientBuilder() {
		this.jwtProvider = () -> "";
	}
	
	public DataAccessClientBuilder baseUrl(String baseUrl) {
		this.baseUrl = UrlUtil.formatBaseUrl(baseUrl);
		return this;
	}
		
	public DataAccessClientBuilder jwtProvider(Supplier<String> jwtProvider) {
		this.jwtProvider = jwtProvider;
		return this;
	}
		
	public DataAccessClient build() {
		DataAccessClient client = new DataAccessClient(this.baseUrl);
		client.setJwtProvider(jwtProvider);
		return client;
	}

}
