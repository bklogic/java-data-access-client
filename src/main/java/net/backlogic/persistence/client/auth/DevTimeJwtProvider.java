package net.backlogic.persistence.client.auth;

import static net.backlogic.persistence.client.handler.HTTP.APPLICATION_JSON;
import static net.backlogic.persistence.client.handler.HTTP.HTTP_ACCEPT;
import static net.backlogic.persistence.client.handler.HTTP.HTTP_CONTENT_TYPE;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.backlogic.persistence.client.handler.JsonHandler;

public class DevTimeJwtProvider implements Supplier<String> {
	private static final Logger LOGGER = LoggerFactory.getLogger(DevTimeJwtProvider.class);
	private static long bufferSecs = 10;

	private String authEndpoint;
	private AuthRequest authRequest;
	
	private String jwt;
	private long expiryTime = 0;
	
	private JsonHandler jsonHandler;
	
	public DevTimeJwtProvider(DevTimeCredential credential) {
		this.authEndpoint = credential.getAuthEndpoint();
		this.authRequest = new AuthRequest(credential.getServiceKey(), credential.getServiceSecret());
		this.jsonHandler = new JsonHandler();
	}

	@Override
	public String get() {
		if ( jwt == null || this.expireSoon() ) {
			Authorization auth = this.auth();
			this.jwt = auth.getJwt();
			this.expiryTime = auth.getExpiryTime();
		}
		return jwt;
	}

	private Authorization auth () {
		// default auth
        Authorization auth = new Authorization("", 0);

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
        	auth =  (Authorization) this.jsonHandler.toObject(response.body(), Authorization.class);
        }
        else {
        	LOGGER.error("Error in obtaining DevTime authorization.", statusCode + " - " + response.body());
        }
		return auth;
	}

    private boolean expireSoon() {
		// Get the current time in seconds since epoch
		long currentTime = System.currentTimeMillis() / 1000;
		return currentTime > (expiryTime + bufferSecs);
    }

}
