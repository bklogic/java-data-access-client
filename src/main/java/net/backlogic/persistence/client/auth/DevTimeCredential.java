package net.backlogic.persistence.client.auth;

public class DevTimeCredential {
	private String authEndpoint;
	private String serviceKey;
	private String serviceSecret;
	
	public DevTimeCredential() {
	}
	public DevTimeCredential(String authEndpoint, String serviceKey, String serviceSecret) {
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
