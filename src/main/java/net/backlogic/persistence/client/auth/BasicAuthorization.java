package net.backlogic.persistence.client.auth;

/**
 * <p>Authorization received by BasicJwtProvider from auth endpoint.</p>
 * <p>
 *     The DefaultAuthorization includes a JWT token and its expiryTime, so that the client does not need
 * to parse the token to know the expiryTime. The expiryTime should be in format of number of milliseconds since the epoch.
 * </p>
 */
public class BasicAuthorization {
	private String jwt;
	long expiryTime;
	
	public BasicAuthorization() {};
	
	public BasicAuthorization(String jwt, long expiryTime) {
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
