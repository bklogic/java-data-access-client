package net.backlogic.persistence.client.handler;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.ServerException;

/**
 * Handling JSON-Object conversion.
 */
public class JsonHandler {
    private ObjectMapper mapper;

    /*
     * Construct JsonHandler
     */
    public JsonHandler() {
        this.mapper = new ObjectMapper();
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
            throw new DataAccessException(DataAccessException.JsonException, "Error in convert", e);
        }
        return jsonString;
    }

    public Object toReturnType (String jsonString, ReturnType returnType, Class<?> elementType) {
    	if (jsonString.startsWith("[")) {
    		returnType = ReturnType.LIST;
    	}
        switch (returnType) {
            case LIST:
                return this.toList(jsonString, elementType);
            case OBJECT:
                return this.toObject(jsonString, elementType);
            case VALUE:
                return this.readValue(jsonString);
            case MAP:
                return this.toMap(jsonString);
            default:
                return null;
        }
    }

    public DataAccessException readException(String jsonString) {
        DataAccessException exception;
        try {
        	ServerException se = (ServerException) toObject(jsonString, ServerException.class);
        	exception = new DataAccessException("ServiceException", se.getStatus() + " - " + se.getMessage());
        } catch (Exception e) {
            exception = new DataAccessException("ExceptionHandlingError", "json text: " + jsonString, e);
        }
        return exception;
    }

    /*
     * To convert a JSON string into object
     */
    public Object toObject(String jsonString, Class<?> objType) {
    	// check json string
    	if (jsonString == null ||  jsonString.equals("")) {
    		return null;
    	}
    	
    	// convert to object
        Object object = null;
        try {
            object = mapper.readValue(jsonString, objType);
        } catch (JsonParseException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonParseException", e);
        } catch (JsonMappingException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonMappingException", e);
        } catch (IOException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonIOException", e);
        }
        return object;
    }

    /*
     * To convert a JSON string into list
     */
    public List<?> toList(String jsonString, Class<?> elementType) {
        List<?> list = null;
        JavaType objectCollection = mapper.getTypeFactory().constructCollectionType(LinkedList.class, elementType);
        try {
            list = mapper.readValue(jsonString, objectCollection);
        } catch (JsonParseException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonParseException", e);
        } catch (JsonMappingException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonMappingException", e);
        } catch (IOException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonIOException", e);
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
            throw new DataAccessException(DataAccessException.JsonException, "JsonParseException", e);
        } catch (JsonMappingException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonMappingException", e);
        } catch (IOException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonIOException", e);
        }
        return set;
    }

    public Object readValue(String jsonString) {
    	// check string
    	if ( jsonString == null || jsonString.equals("")) {
    		return null;
    	}
    	
        // parse json string
        Map<String, Object> map;
        try {
            map = mapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonProcessingException", e);
        }
        
        // find value
        Object value = null;
        if (map.isEmpty()) {
            value = null;
        } else if (map.size() == 1) {
            for (Object val: map.values()) {
                value = val;
            }
        } else {
            throw new DataAccessException(DataAccessException.JsonException, "Multiple fields in service output: " + jsonString);
        }
        
        return value;
    }
 
    public Object toMap(String jsonString) {
        Map<String, Object> map;
        try {
            map = mapper.readValue(jsonString, Map.class);
        } catch (JsonProcessingException e) {
            throw new DataAccessException(DataAccessException.JsonException, "JsonProcessingException", e);
        }
        return map;
    }
    
}


