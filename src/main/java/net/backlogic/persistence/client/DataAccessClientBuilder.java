package net.backlogic.persistence.client;

import net.backlogic.persistence.client.auth.BasicJwtProvider;
import net.backlogic.persistence.client.auth.BasicJwtProviderProperties;
import net.backlogic.persistence.client.auth.JwtProvider;
import net.backlogic.persistence.client.auth.SimpleJwtProvider;
import net.backlogic.persistence.client.proxy.UrlUtil;

/**
 * <p>Builder for configuring and building data access client.</p>
 */
public class DataAccessClientBuilder {
	private String baseUrl;
	private JwtProvider jwtProvider;
	private String jwt;
	private BasicJwtProviderProperties basicJwtProviderProperties;
	private boolean logRequest;

	/**
	 * Construct a builder instance, which shall be done with
	 * DataAccessClient.builder().
	 */
	public DataAccessClientBuilder() {
		this.logRequest = false;
	}

	/**
	 * Configure baseUrl for data access client.
	 * @param baseUrl	baseUrl for data access services
	 * @return this builder instance
	 */
	public DataAccessClientBuilder baseUrl(String baseUrl) {
		this.baseUrl = UrlUtil.formatBaseUrl(baseUrl);
		return this;
	}
	
	/**
	 * Configure JWTProvider for data access client.
	 * If configured, data access client uses this provider
	 * to acquire a BEARER token for service authentication.
	 * This client has an implementation of JwtProvider in BasicJwtProvider based on BasicJwtCredential.
	 * @param jwtProvider JWT provider for supplying JWT token
	 * @return	this builder instance
	 */
	public DataAccessClientBuilder jwtProvider(JwtProvider jwtProvider) {
		if (jwtProvider != null) {
			this.jwtProvider = jwtProvider;
		}
		return this;
	}

	/**
	 * Configure data access client to use BasicJwtProvider with supplied properties.
	 * @param properties properties for BasicJwtProvider
	 * @return this builder instance
	 */
	public DataAccessClientBuilder basicJwtProviderProperties(BasicJwtProviderProperties properties) {
		this.basicJwtProviderProperties = properties;
		return  this;
	}

	/**
	 * Configure data access client to use SimpleJwtProvider with supplied jwt token.
	 * @param jwt jwt token for the SimpleJwtProvider
	 * @return this builder instance
	 */
	public DataAccessClientBuilder jwt(String jwt) {
		this.jwt = jwt;
		return  this;
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
	 * @return	an instance of DataAccessClient.
	 */
	public DataAccessClient build() {
		DataAccessClient client = new DataAccessClient(this.baseUrl);
		client.logRequest(this.logRequest);
		if (jwtProvider != null) {
			client.setJwtProvider(jwtProvider);
		} else if (basicJwtProviderProperties != null) {
			client.setJwtProvider(new BasicJwtProvider().set(basicJwtProviderProperties));
		} else if (jwt != null) {
			client.setJwtProvider(new SimpleJwtProvider().setJwt(jwt));
		} else {
			client.setJwtProvider(new SimpleJwtProvider().setJwt(""));
		}
		return client;
	}

}
