/**
 * 
 */
package net.backlogic.persistent.client;

import net.backlogic.persistence.client.PersistenceInterface;
import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Service;

/**
 * @author Ken
 *
 */
@Command
public interface CustomerCommand extends PersistenceInterface {
	
	@Service("mod/RemoveAllCustomers")
	public void removeAllCustomers();
}
