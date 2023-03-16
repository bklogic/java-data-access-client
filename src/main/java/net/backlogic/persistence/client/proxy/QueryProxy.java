/**
 *
 */
package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.PersistenceException;
import net.backlogic.persistence.client.annotation.BacklogicQuery;
import net.backlogic.persistence.client.annotation.Name;
import net.backlogic.persistence.client.handler.ServiceHandler;

import java.lang.reflect.Method;


/**
 * Proxy for Query interface
 */
public class QueryProxy extends PersistenceProxy {

    public QueryProxy(ServiceHandler serviceHandler) {
        super(serviceHandler);
    }

    /*
     * get service url
     */
    @Override
    protected String getServiceUrl(Method method) {
        String interfaceUrl, methodUrl;

        //interface url
        BacklogicQuery interfaceAnnotation = method.getDeclaringClass().getAnnotation(BacklogicQuery.class);
        interfaceUrl = interfaceAnnotation.value();

        //method url
        Name urlAnnotation = method.getAnnotation(Name.class);
        if (urlAnnotation == null) {
            throw new PersistenceException(PersistenceException.InterfaceException, "InvalidInterface", "Url annotation is missing");
        } else {
            methodUrl = urlAnnotation.value();
        }

        return UrlUtil.getUrl(interfaceUrl, methodUrl);
    }

}
