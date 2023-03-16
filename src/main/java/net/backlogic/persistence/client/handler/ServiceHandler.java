/**
 *
 */
package net.backlogic.persistence.client.handler;

/**
 * @author Ken
 * Created on 10/22/2017
 * <p>
 * Responsible for handle HTTP service request. Interface is for convenience of Mock Test
 */
@FunctionalInterface
public interface ServiceHandler {
    public Object invoke(String serviceUrl, Object serviceInput, ReturnType returnType, Class<?> elementType);

}
