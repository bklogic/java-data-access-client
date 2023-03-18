/**
 *
 */
package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.annotation.*;
import net.backlogic.persistence.client.handler.ServiceHandler;

import java.lang.reflect.Method;

/**
 * Repository Proxy class. Responsible for handling Repository requests.
 */
public class RepositoryProxy extends PersistenceProxy {
	
	private String interfaceUrl;

    public RepositoryProxy(ServiceHandler serviceHandler, String interfaceUrl) {
        super(serviceHandler);
        this.interfaceUrl = interfaceUrl;
    }

    @Override
    protected String getServiceUrl(Method method) {
        String methodUrl;

//        //interface url
//        RepositoryService interfaceAnnotation = method.getDeclaringClass().getAnnotation(RepositoryService.class);
//        interfaceUrl = interfaceAnnotation.value();
//        if (interfaceUrl == null || interfaceUrl == "") {
//            interfaceUrl = interfaceAnnotation.url();
//        }

        //method url
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
            throw new DataAccessException(DataAccessException.InterfaceException, "Repository Interface method is not properly annotated");
        }

        return UrlUtil.getUrl(this.interfaceUrl, methodUrl);
    }

}

