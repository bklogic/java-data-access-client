/**
 * 
 */
package net.backlogic.persistence.client.handler;

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
		  //mapper
		  ObjectMapper mapper = new ObjectMapper();
		  
		  //handle void
		  if ( objType.getName() == "void") {
			  return null;
		  }
		  
//TODO:  test and enhance for data types, dates, bytes[], AND structure types: lIST, ETC	  
		  
//		  else if (objType == java.util.List.class){
//			  objType = mapper.getTypeFactory().constructCollectionType(java.util.List.class, Foo.class)
//		  }
		  
		  
//		  List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});		  
		  
		  
		  //process
		  Object object = null;
			try {
				if (objType == java.util.List.class) {
					
				}
				
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
