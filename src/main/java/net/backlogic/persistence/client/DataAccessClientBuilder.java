package net.backlogic.persistence.client;

import java.util.function.Supplier;

import net.backlogic.persistence.client.auth.DevTimeCredential;
import net.backlogic.persistence.client.auth.DevTimeJwtProvider;
import net.backlogic.persistence.client.proxy.UrlUtil;

/**
 * Builder for data access client.
 *
 */
public class DataAccessClientBuilder {
	private String baseUrl;
	private DevTimeCredential credential;
	private Supplier<String> jwtProvider;
	private boolean logRequest;

	/**
	 * Construct a builder instance, which is normally done by calling
	 * DataAccessClient.builder().
	 */
	public DataAccessClientBuilder() {
		this.jwtProvider = () -> "";
		this.logRequest = false;
	}

	/**
	 * Configure baseUrl for data access client
	 * @param baseUrl	baseUrl for data access services
	 * @return this builder instance
	 */
	public DataAccessClientBuilder baseUrl(String baseUrl) {
		this.baseUrl = UrlUtil.formatBaseUrl(baseUrl);
		return this;
	}
	
	/**
	 * Configure JWTProvider for data access client.
	 * @param jwtProvider JWT provider for supplying JWT token
	 * @return	this builder instance
	 */
	public DataAccessClientBuilder jwtProvider(Supplier<String> jwtProvider) {
		if (jwtProvider != null) {
			this.jwtProvider = jwtProvider;
		}
		return this;
	}

	/**
	 * Configure DevTime credential for data access client.
	 * @param credential DevTime credential for accessing DevTime data access services
	 * @return	this builder instance
	 */
	public DataAccessClientBuilder devTimeCredential(DevTimeCredential credential) {
		this.credential = credential;
		return this;
	}

	/**
	 * Configure logRequest properties for data access client.
	 * @param logRequest	log request and response if set true.
	 * @return	this builder instance
	 */
	public DataAccessClientBuilder logRequest(boolean logRequest) {
		this.logRequest = logRequest;
		return this;
	}

	/**
	 * Build data access client
	 * @return	an instance of DataAccessClient
	 */
	public DataAccessClient build() {
		DataAccessClient client = new DataAccessClient(this.baseUrl);
		client.setJwtProvider((credential == null) ? this.jwtProvider : new DevTimeJwtProvider(this.credential));
		client.logRequest(this.logRequest);
		return client;
	}

}
