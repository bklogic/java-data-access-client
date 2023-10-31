package net.backlogic.persistence.client.auth;

import net.backlogic.persistence.client.handler.JsonHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

import static net.backlogic.persistence.client.handler.HTTP.*;

/**
 * <p>A basic implementation of JwtProvider based on a auth service.</p>
 * <p>
 * Beside serviceKey and serviceSecrete, the BasicJwtCredential includes an authEndpoint that
 * this BasicJwtProvider can call to get a JWT token.
 * The auth service is expected to accept an instance of BasicAuthRequest and return an instance of BasicAuthorization.
 * The BasicJwtProvider automatically renews the JWT token when it is expired.
 * </p>
 */
public class BasicJwtProvider implements JwtProvider  {
	private static final String AUTH_ENDPOINT = "authEndPoint";
	private static final String SERVICE_KEY = "serviceKey";
	private static final String SERVICE_SECRET = "serviceSecret";
	private static final Logger LOGGER = LoggerFactory.getLogger(BasicJwtProvider.class);
	private static final long TIME_BUFFER_IN_SECONDS = 10;

	private String authEndpoint;
	private BasicAuthRequest authRequest;
	
	private String jwt;
	private long expiryTime = 0;
	
	private JsonHandler jsonHandler;

	@Override
	public String get() {
		if ( jwt == null || this.expireSoon() ) {
			BasicAuthorization auth = this.auth();
			this.jwt = auth.getJwt();
			this.expiryTime = auth.getExpiryTime();
		}
		return jwt;
	}

	@Override
	public void refresh() {
		this.jwt = null;  get();
	}

	@Override
	public void set(Properties properties) {
		this.authEndpoint = properties.getProperty(AUTH_ENDPOINT);
		this.authRequest = new BasicAuthRequest(properties.getProperty(SERVICE_KEY), properties.getProperty(SERVICE_SECRET));
	}

	public JwtProvider set(BasicJwtProviderProperties properties) {
		this.authEndpoint = properties.getAuthEndpoint();
		this.authRequest = new BasicAuthRequest(properties.getServiceKey(), properties.getServiceSecret());
		return this;
	}

	private BasicAuthorization auth () {
		// default auth
        BasicAuthorization auth = new BasicAuthorization("", 0);

        // request auth
		HttpResponse<String> response;		
		try {
	        HttpRequest request = HttpRequest.newBuilder()
	                .uri(new URI(this.authEndpoint))
	                .header(HTTP_CONTENT_TYPE, APPLICATION_JSON)
	                .header(HTTP_ACCEPT, APPLICATION_JSON)
	                .POST(HttpRequest.BodyPublishers.ofString(this.jsonHandler.toJson(this.authRequest)))
	                .build();	        
	        response = HttpClient.newHttpClient()
	        		.send(request, HttpResponse
	        				.BodyHandlers
	        				.ofString());			
		} catch (Exception e) {
        	LOGGER.error("Exception in requesting DevTime authorization.", e);
        	return auth;
		}
		
        // handle response
        int statusCode = response.statusCode();
        if (statusCode == 200) {
        	auth =  (BasicAuthorization) this.jsonHandler.toObject(response.body(), BasicAuthorization.class);
        }
        else {
        	LOGGER.error("Error in obtaining DevTime authorization.", statusCode + " - " + response.body());
        }
		return auth;
	}

    private boolean expireSoon() {
		Instant expiry = Instant.ofEpochMilli(expiryTime);
		Instant cutoff = Instant.now().plus(TIME_BUFFER_IN_SECONDS, ChronoUnit.SECONDS);
		return cutoff.isAfter(expiry);
    }

}
