package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

/*
 * Determine if a method is a service method. If yes, return method url.
 */
public interface ServiceMethodFinder {
	/**
	 * Find url of method service, return null if not a service method.
	 * @param method Java method object.
	 * @return url of method service.
	 */
	String find(Method method);

	/**
	 * Get return mapping of the service method. For BatchService.
	 * @return return mapping of service method
	 */
	String returnMapping(Method method);
}
