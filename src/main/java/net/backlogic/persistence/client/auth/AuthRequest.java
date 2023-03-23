package net.backlogic.persistence.client.auth;

public class AuthRequest {
	private String serviceKey;
	private String serviceSecret;
	
	public AuthRequest(String serviceKey, String serviceSecret) {
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
