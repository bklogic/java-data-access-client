/**
 *
 */
package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.annotation.QueryService;
import net.backlogic.persistence.client.annotation.Query;
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
        QueryService interfaceAnnotation = method.getDeclaringClass().getAnnotation(QueryService.class);
        interfaceUrl = interfaceAnnotation.value();

        //method url
        Query urlAnnotation = method.getAnnotation(Query.class);
        if (urlAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, "Url annotation is missing");
        } else {
            methodUrl = urlAnnotation.value();
        }

        return UrlUtil.getUrl(interfaceUrl, methodUrl);
    }

}
