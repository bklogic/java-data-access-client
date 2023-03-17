package net.backlogic.persistence.client.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import net.backlogic.persistence.client.DataAccessException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        switch (returnType) {
            case LIST:
                return this.toList(jsonString, elementType);
            case OBJECT:
                return this.toObject(jsonString, elementType);
            case VALUE:
                return this.readValue(jsonString);
            default:
                return null;
        }
    }

    public DataAccessException readException(String jsonString) {
        DataAccessException exception;
        try {
            exception = (DataAccessException) toObject(jsonString, DataAccessException.class);
        } catch (Exception e) {
            exception = new DataAccessException("ExceptionHandlingError", "json text: " + jsonString, e);
        }
        return exception;
    }

    /*
     * To convert a JSON string into object
     */
    public Object toObject(String jsonString, Class<?> objType) {
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
        JavaType objectCollection = mapper.getTypeFactory().constructCollectionType(List.class, elementType);
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
        Map<String, Object> map;
        // parse json string
        try {
            TypeReference<HashMap<String,Object>> typeRef = new TypeReference<HashMap<String,Object>>() {};
            map = mapper.readValue(jsonString, typeRef);
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

}