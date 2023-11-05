package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.annotation.Query;

public class QueryServiceMethodFinder implements ServiceMethodFinder {

	@Override
	public String find(Method method) {
        String methodUrl = null;
        Query annotation = method.getAnnotation(Query.class);
        if (annotation != null) {
            methodUrl = annotation.value();
        } 
    	return methodUrl;
	}

    @Override
    public String returnMapping(Method method) {
        return null;
    }
}
