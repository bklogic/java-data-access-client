package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.ReturnMapping;

public class CommandServiceMethodFinder implements ServiceMethodFinder {
	@Override
	public String find(Method method) {
        String methodUrl = null;
        Command annotation = method.getAnnotation(Command.class);
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
