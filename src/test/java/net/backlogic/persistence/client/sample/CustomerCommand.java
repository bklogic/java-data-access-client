/**
 * 
 */
package net.backlogic.persistence.client.sample;

import net.backlogic.persistence.client.PersistenceInterface;
import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Dao;
import net.backlogic.persistence.client.annotation.Url;

/**
 * @author Ken
 *
 */
@Command("/mod")
public interface CustomerCommand extends PersistenceInterface {
	
	@Url("/RemoveAllCustomers")
	public void removeAllCustomers();
}
