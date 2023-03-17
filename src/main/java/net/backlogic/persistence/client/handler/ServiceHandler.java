/**
 *
 */
package net.backlogic.persistence.client.handler;

/**
 * Responsible for handle HTTP service request. Interface is for convenience of Mock Test.
 */
@FunctionalInterface
public interface ServiceHandler {
    public Object invoke(String serviceUrl, Object serviceInput, ReturnType returnType, Class<?> elementType);

}
