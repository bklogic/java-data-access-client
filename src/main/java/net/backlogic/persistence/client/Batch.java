package net.backlogic.persistence.client;

import java.util.List;

/**
 * The base class for batched services. A batch service interface shall be annotated with the
 * {@literal @}BatchService annotation and extends this class, which provides method to
 * invoke the batched services.
 */
public interface Batch {
	
	public Object[] run();
	public Object[] get();
	public List<Object> save();
	
	public void clean();
	
}
