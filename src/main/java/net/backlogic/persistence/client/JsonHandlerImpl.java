/**
 * 
 */
package net.backlogic.persistence.client;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Ken
 * Created on 10/23/2017
 * 
 * Responsible for handling JSON-Object conversion 
 */
public class JsonHandlerImpl implements JsonHandler {
	  /*
	   * To concert an object into JSON string
	   */
	  public String toJson(Object object) {
		  //for null object
		  if ( object == null ) {
			  return "{}";
		  }
		  
		  //else
		  ObjectMapper mapper = new ObjectMapper();	
		  String jsonString = "";
			try {
				jsonString = mapper.writeValueAsString(object);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		  return jsonString;
	  }
	  
	  /*
	   * To convert a JSON string into object
	   */
	  public Object toObject(String jsonString, Class<?> objType) {
		  //handle void
		  if (objType.getName() == "void") {
			  return null;
		  }
		  
		  //process
		  ObjectMapper mapper = new ObjectMapper();	
		  Object object = null;
			try {
				object = mapper.readValue(jsonString, objType);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		  return object;
	  }
	  	  
}
