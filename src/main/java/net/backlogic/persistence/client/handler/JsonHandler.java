/**
 * 
 */
package net.backlogic.persistence.client.handler;

import java.util.List;
import java.util.Set;

/**
 * @author Ken
 * Created on 10/24/2015
 * 
 * Interface to provide methods to convert object to json and json to object.
 * Interfaced used with intention to plug in user implemented JsonHandler.
 */
public interface JsonHandler {

	public String toJson(Object object);
	
	public Object toObject(String jsonString, Class<?> objType);

	public List<?> toList(String jsonString, Class<?> elementType);

	public Set<?> toSet(String jsonString, Class<?> elementType);
}
