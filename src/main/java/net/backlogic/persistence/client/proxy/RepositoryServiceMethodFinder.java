package net.backlogic.persistence.client.proxy;

import java.lang.reflect.Method;

import net.backlogic.persistence.client.annotation.Create;
import net.backlogic.persistence.client.annotation.Delete;
import net.backlogic.persistence.client.annotation.Merge;
import net.backlogic.persistence.client.annotation.Read;
import net.backlogic.persistence.client.annotation.Save;
import net.backlogic.persistence.client.annotation.Update;

public class RepositoryServiceMethodFinder implements ServiceMethodFinder {
	@Override
	public String find(Method method) {
        String methodUrl;
        if (method.getAnnotation(Read.class) != null) {
            methodUrl = "/read";
        } else if (method.getAnnotation(Create.class) != null) {
            methodUrl = "/create";
        } else if (method.getAnnotation(Update.class) != null) {
            methodUrl = "/update";
        } else if (method.getAnnotation(Delete.class) != null) {
            methodUrl = "/delete";
        } else if (method.getAnnotation(Save.class) != null) {
            methodUrl = "/save";
        } else if (method.getAnnotation(Merge.class) != null) {
            methodUrl = "/merge";
        } else {
            methodUrl = null;
        }
    	return methodUrl;
	}
}
