package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.DataAccessException;
import net.backlogic.persistence.client.annotation.CommandService;
import net.backlogic.persistence.client.annotation.QueryService;
import net.backlogic.persistence.client.annotation.RepositoryService;
import net.backlogic.persistence.client.handler.DefaultServiceHandler;
import net.backlogic.persistence.client.handler.ServiceHandler;

import java.lang.reflect.Proxy;

/**
 * Responsible for generating persistence proxy from interface
 */
public class ProxyFactory {
    // service handler
    private final ServiceHandler serviceHandler;

    /*
     * Construct proxy generator
     */
    public ProxyFactory(String baseUrl) {
        this.serviceHandler = new DefaultServiceHandler(baseUrl);
    }

    /*
     * Construct proxy generator with mock service handler
     */
    public ProxyFactory(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    public Object createRepository(Class<?> repositoryType) {
        //validate repository interface
        RepositoryService repositoryAnnotation = repositoryType.getAnnotation(RepositoryService.class);
        if (repositoryAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, repositoryType.getName() + " is not repository interface");
        }

        //instantiate repository interface proxy
        String interfaceUrl = repositoryAnnotation.value();
        Object proxy = Proxy.newProxyInstance(
                repositoryType.getClassLoader(), new Class[]{repositoryType}, new RepositoryProxy(serviceHandler, interfaceUrl)
        );

        return proxy;
    }


    public Object createQuery(Class<?> queryType) {
        //validate repository interface
        QueryService queryAnnotation = queryType.getAnnotation(QueryService.class);
        if (queryAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, queryType.getName() + " is not query interface");
        }

        //instantiate query interface proxy
        Object proxy = Proxy.newProxyInstance(
                queryType.getClassLoader(), new Class[]{queryType}, new QueryProxy(serviceHandler)
        );

        return proxy;
    }

    public Object createCommand(Class<?> commandType) {
        //validate repository interface
        CommandService persistAnnotation = commandType.getAnnotation(CommandService.class);
        if (persistAnnotation == null) {
            throw new DataAccessException(DataAccessException.InterfaceException, commandType.getName() + " is not command interface");
        }

        //instantiate command interface proxy
        Object proxy = Proxy.newProxyInstance(
                commandType.getClassLoader(), new Class[]{commandType}, new CommandProxy(serviceHandler)
        );

        return proxy;
    }

}
