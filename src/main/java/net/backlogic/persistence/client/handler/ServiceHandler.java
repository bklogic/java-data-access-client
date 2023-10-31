/**
 *
 */
package net.backlogic.persistence.client.handler;

import net.backlogic.persistence.client.auth.JwtProvider;

/**
 * Responsible for handle HTTP service request.
 */
public interface ServiceHandler {
	
    public Object invoke(String serviceUrl, Object serviceInput, ReturnType returnType,
                         Class<?> elementType, boolean retryOn403);

    public void setJwtProvider(JwtProvider jwtProvider);
    
    public void logRequest(boolean logRequest);
    
}
