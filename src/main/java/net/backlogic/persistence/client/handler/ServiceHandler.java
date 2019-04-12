/**
 * 
 */
package net.backlogic.persistence.client.handler;

/**
 * @author Ken
 * Created on 10/22/2017
 * 
 * Responsible for handle HTTP service request. Interface is for convenience of Mock Test
 */
@FunctionalInterface
public interface ServiceHandler {
	
	public String invoke(String serviceUrl, String serviceInput);	
	
}
