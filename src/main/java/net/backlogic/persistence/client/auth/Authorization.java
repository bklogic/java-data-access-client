package net.backlogic.persistence.client.auth;

public class Authorization {
	private String jwt;
	long expiryTime;
	
	public Authorization() {};
	
	public Authorization(String jwt, long expiryTime) {
		this.jwt = jwt;
		this.expiryTime = expiryTime;
	}
	
	public String getJwt() {
		return jwt;
	}
	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	public long getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}
}
