/**
 *
 */
package net.backlogic.persistence.client.handler;

import java.util.function.Supplier;

/**
 * Responsible for handle HTTP service request.
 */
public interface ServiceHandler {
	
    public Object invoke(String serviceUrl, Object serviceInput, ReturnType returnType, Class<?> elementType);

    public void setJwtProvider(Supplier<String> jwtProvider);
}
