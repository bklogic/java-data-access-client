package net.backlogic.persistence.client;

import java.util.List;

public interface Batch {
	
	public Object[] run();
	public Object[] get();
	public List<Object> save();
	
	public void clean();
	
}
