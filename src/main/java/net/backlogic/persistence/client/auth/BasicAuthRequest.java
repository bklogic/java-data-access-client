package net.backlogic.persistence.client.auth;

/**
 * <p>Authentication request sent by BasicJwtProvided to auth endpoint.</p>
 */
public class BasicAuthRequest {
	private String serviceKey;
	private String serviceSecret;
	
	public BasicAuthRequest(String serviceKey, String serviceSecret) {
		super();
		this.serviceKey = serviceKey;
		this.serviceSecret = serviceSecret;
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
