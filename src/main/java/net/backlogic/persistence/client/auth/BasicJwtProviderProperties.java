package net.backlogic.persistence.client.auth;

/**
 * <p>Properties for the BasicJwtProvider.</p>
 * <p>The BasicJwtProperties includes a auth service endpoint that BasicJwtProvider can call to
 * get JWT token.</p>
 */
public class BasicJwtProviderProperties {
	private String authEndpoint;
	private String serviceKey;
	private String serviceSecret;
	
	public BasicJwtProviderProperties() {
	}
	public BasicJwtProviderProperties(String authEndpoint, String serviceKey, String serviceSecret) {
		this.authEndpoint = authEndpoint;
		this.serviceKey = serviceKey;
		this.serviceSecret = serviceSecret;
	}
	public String getAuthEndpoint() {
		return authEndpoint;
	}
	public void setAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
	}
	public String getServiceKey() {
		return serviceKey;
	}
	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}
	public String getServiceSecret() {
		return serviceSecret;
	}
	public void setServiceSecret(String serviceSecret) {
		this.serviceSecret = serviceSecret;
	}

}
