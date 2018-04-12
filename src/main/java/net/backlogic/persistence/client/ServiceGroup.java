/**
 * 
 */
package net.backlogic.persistence.client;

/**
 * @author Ken
 * Created on 10/23/2017
 * 
 * Provides Methods to create, commit and cancel groups of service requests 
 */
public interface ServiceGroup {
	/*
	 * To create a service group.  Return a new service group Id
	 */
	public String create();
	/*
	 * To commit a service group.
	 */
	public void commit(String groupId);
	/*
	 * To cancel a service group.
	 */
	public void cancel(String groupId);
}
