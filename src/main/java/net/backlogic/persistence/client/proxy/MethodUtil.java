package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MethodUtil {
	
	public static String createMethodKey(Method m) {
    	String key = m.getName();
    	for (Parameter p : m.getParameters()) {
    		key = key + "." + p.getName();
    	}   	
    	return key;
    }
	
}
