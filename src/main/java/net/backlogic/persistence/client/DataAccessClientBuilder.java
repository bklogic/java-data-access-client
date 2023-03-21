package net.backlogic.persistence.client;

import net.backlogic.persistence.client.proxy.UrlUtil;

public class DataAccessClientBuilder {
	private String baseUrl;
	
	public DataAccessClientBuilder baseUrl(String baseUrl) {
		this.baseUrl = UrlUtil.formatBaseUrl(baseUrl);
		return this;
	}
		
	public DataAccessClient build() {
		return new DataAccessClient(this.baseUrl);
	}

}
