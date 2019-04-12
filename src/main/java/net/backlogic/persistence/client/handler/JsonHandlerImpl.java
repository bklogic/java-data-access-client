/**
 * 
 */
package net.backlogic.persistence.client.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.backlogic.persistence.client.PersistenceException;

/**
 * @author Ken
 * Created on 10/23/2017
 * 
 * Responsible for handling JSON-Object conversion 
 */
public class JsonHandlerImpl implements JsonHandler {
	 
	  private ObjectMapper mapper;
	
	
	  /*
	   * Construct JsonHandler
	   */
	  public JsonHandlerImpl() {
		  this.mapper = new ObjectMapper();
//		  this.mapper.setDateFormat(new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSS'Z'"));
		  this.mapper.setDateFormat(new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSS'Z'"));
		  this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		  this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	  }
	  
	  
	  /*
	   * To convert an object into JSON string
	   */
	  public String toJson(Object object) {
		  //for null object
		  if ( object == null ) {
			  return "{}";
		  }
		  
		  //else
		  String jsonString = "";
		  try {
			  jsonString = mapper.writeValueAsString(object);
		  } catch (JsonProcessingException e) {
			  throw new PersistenceException(PersistenceException.JsonException, "UnknownJsonException", e);
		  }
		  return jsonString;
	  }
	  
	  

	  /*
	   * To convert a JSON string into object
	   */
	  public Object toObject(String jsonString, Class<?> objType) {
		    Object object = null;
			try {
				object = mapper.readValue(jsonString, objType);
			} catch (JsonParseException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonParseException", e);
			} catch (JsonMappingException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonMappingException", e);
			} catch (IOException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonIOException", e);
			}
		
			return object;
	  }
	  
	  
	  /*
	   * To convert a JSON string into list
	   */
	  public List<?> toList(String jsonString, Class<?> elementType) {
		    List<?> list = null;
			JavaType objectCollection = mapper.getTypeFactory().constructCollectionType(List.class, elementType);
			try {
				list = mapper.readValue(jsonString, objectCollection);
			} catch (JsonParseException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonParseException", e);
			} catch (JsonMappingException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonMappingException", e);
			} catch (IOException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonIOException", e);
			}
			return list;
	  }
	  
	  
	  /*
	   * To convert a JSON string into list
	   */
	  public Set<?> toSet(String jsonString, Class<?> elementType) {
		    Set<?> set = null;
			JavaType objectCollection = mapper.getTypeFactory().constructCollectionType(Set.class, elementType);
			try {
				set = mapper.readValue(jsonString, objectCollection);
			} catch (JsonParseException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonParseException", e);
			} catch (JsonMappingException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonMappingException", e);
			} catch (IOException e) {
				  throw new PersistenceException(PersistenceException.JsonException, "JsonIOException", e);
			}
			return set;
	  }
	  	  
}
