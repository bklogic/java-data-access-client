package net.backlogic.persistence.client;

import java.util.List;
import java.util.Optional;

import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Update;

public interface GenericRepository<T> {
	
	@Create
	public T create(T object);
	
	@Update
	public T update(T object);
	
	@Delete
	public void delete(T object);
	
	@Read
	public List<T> getAll();
	
}
