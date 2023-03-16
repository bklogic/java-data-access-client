/**
 *
 */
package net.backlogic.persistence.client.handler;

import java.util.List;
import java.util.Set;

/**
 * @author Ken
 * Created on 10/24/2015
 * <p>
 * Interface to provide methods to convert object to json and json to object.
 * Interfaced used with intention to plug in user implemented JsonHandler.
 */
public interface JsonHandler {

    String toJson(Object object);

    Object toObject(String jsonString, Class<?> objType);

    List<?> toList(String jsonString, Class<?> elementType);

    Set<?> toSet(String jsonString, Class<?> elementType);
}
