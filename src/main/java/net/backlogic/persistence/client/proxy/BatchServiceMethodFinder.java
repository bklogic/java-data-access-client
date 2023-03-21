package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Merge;
import net.backlogic.persistence.client.annotation.Query;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Save;
import net.backlogic.persistence.client.annotation.Update;

public class BatchServiceMethodFinder implements ServiceMethodFinder {

	@Override
	public String find(Method method) {
        String methodUrl;
        if (method.getAnnotation(Read.class) != null) {
            methodUrl = method.getAnnotation(Read.class).value() + "/read";
            
        } else if (method.getAnnotation(Create.class) != null) {
            methodUrl = method.getAnnotation(Create.class).value() + "/create";
            
        } else if (method.getAnnotation(Update.class) != null) {
            methodUrl = method.getAnnotation(Update.class).value() + "/update";
            
        } else if (method.getAnnotation(Delete.class) != null) {
            methodUrl = method.getAnnotation(Delete.class).value() + "/delete";
            
        } else if (method.getAnnotation(Save.class) != null) {
            methodUrl = method.getAnnotation(Save.class).value() + "/save";
            
        } else if (method.getAnnotation(Merge.class) != null) {
            methodUrl = method.getAnnotation(Merge.class).value() + "/merge";
            
        } else if (method.getAnnotation(Query.class) != null) {
            methodUrl = method.getAnnotation(Query.class).value();
            
        } else if (method.getAnnotation(Command.class) != null) {
            methodUrl = method.getAnnotation(Command.class).value();
            
        } else {
            methodUrl = null;
        }
    	return methodUrl;
	}

}
