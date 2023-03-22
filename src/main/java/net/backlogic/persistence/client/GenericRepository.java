package net.backlogic.persistence.client;

import java.util.List;

import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Merge;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Save;
import net.backlogic.persistence.client.annotation.Update;

public interface GenericRepository<T> {
	
	@Create
	public T create(T object);
	
	@Create
	public List<T> create(List<T> objects);
	
	@Update
	public T update(T object);
	
	@Update
	public List<T> update(List<T> objects);
	
	@Delete
	public void delete(T object);
	
	@Delete
	public void delete(List<T> objects);
	
//	@Delete
//	public void deleteById(ID id);
//	
//	@Delete
//	public void deleteById(List<ID> ids);
	
	@Save
	public T save(T object);
	
	@Save
	public List<T> save(List<T> objects);
	
	@Merge
	public T merge(T object);
	
	@Merge
	public List<T> merge(List<T> objects);
	
	@Read
	public List<T> getAll();
	
//	@Read
//	public T getById(ID id);
	
}
