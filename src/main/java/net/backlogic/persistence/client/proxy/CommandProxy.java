/**
 *
 */
package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.annotation.Command;
import net.backlogic.persistence.client.annotation.CommandService;
import net.backlogic.persistence.client.handler.ServiceHandler;

import java.lang.reflect.Method;

public class CommandProxy extends PersistenceProxy {

    public CommandProxy(ServiceHandler serviceHandler) {
        super(serviceHandler);
    }

    /*
     * get service url
     */
    @Override
    protected String getServiceUrl(Method method) {
        String interfaceUrl, methodUrl;

        //interface url
        CommandService interfaceAnnotation = method.getDeclaringClass().getAnnotation(CommandService.class);
        interfaceUrl = interfaceAnnotation.value();

        //method url
        Command urlAnnotation = method.getAnnotation(Command.class);
        if (urlAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, "Url annotation is missing");
        } else {
            methodUrl = urlAnnotation.value();
        }

        return UrlUtil.getUrl(interfaceUrl, methodUrl);
    }

}
