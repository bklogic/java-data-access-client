/**
 *
 */
package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.PersistenceException;
import net.backlogic.persistence.client.annotation.*;
import net.backlogic.persistence.client.handler.ServiceHandler;

import java.lang.reflect.Method;

/**
 * Repository Proxy class. Responsible for handling Repository requests.
 */
public class RepositoryProxy extends PersistenceProxy {

    public RepositoryProxy(ServiceHandler serviceHandler) {
        super(serviceHandler);
    }

    @Override
    protected String getServiceUrl(Method method) {
        String interfaceUrl, methodUrl;

        //interface url
        BacklogicRepository interfaceAnnotation = method.getDeclaringClass().getAnnotation(BacklogicRepository.class);
        interfaceUrl = interfaceAnnotation.value();
        if (interfaceUrl == null || interfaceUrl == "") {
            interfaceUrl = interfaceAnnotation.url();
        }

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
        } else {
            throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Repository Interface method is not properly annotated");
        }

        return UrlUtil.getUrl(interfaceUrl, methodUrl);
    }

}

