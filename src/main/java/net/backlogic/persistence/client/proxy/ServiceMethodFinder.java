package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

/*
 * Determine if a method is a service method. If yes, return method url.
 */
public interface ServiceMethodFinder {
	public String find(Method method);
}
