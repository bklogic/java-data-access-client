/**
 *
 */
package net.backlogic.persistence.client.proxy;

import net.backlogic.persistence.client.handler.ReturnType;
import net.backlogic.persistence.client.handler.ServiceHandler;
import net.backlogic.persistence.client.handler.TypeUtil;

import java.lang.reflect.*;
import java.util.HashMap;
import java.util.List;

/**
 * Proxy Strategy:
 * One proxy per persistence interface. Thus proxy is shared across threads, and should not contain private property.
 */
public abstract class PersistenceProxy implements InvocationHandler {
    //http handler
    ServiceHandler serviceHandler;

    /*
     * Constructor
     */
    public PersistenceProxy(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        try {
            // input
            Object input = getInput(m, args);

            //service url
            String serviceUrl = getServiceUrl(m);

            // output type  TODO: validate supported return types  getGenericReturnType(), getReturnType()
            ReturnType outputType;
            Class<?> elementType;
            Class<?> returnType = m.getReturnType();
            Type genericReturnType = m.getGenericReturnType();
            if (returnType.getName() == "void") {
                outputType = ReturnType.VOID;
                elementType = Void.class;
            } else if (returnType == List.class && genericReturnType instanceof ParameterizedType) {
                outputType = ReturnType.LIST;
                elementType = (Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0];
            } else if (TypeUtil.isPrimitive(returnType)) {
                outputType = ReturnType.VALUE;
                elementType = returnType;
            } else {
                outputType = ReturnType.OBJECT;
                elementType = returnType;
            }

            // invoke service
            Object output = this.serviceHandler.invoke(serviceUrl, input, outputType, elementType);

            //return
            return output;
        } catch (Exception e) {
            throw e;
        }
    }

    abstract protected String getServiceUrl(Method m);

    protected Object getInput(Method method, Object[] args) {
        //get method parameters
        Parameter param;
        Parameter[] params = method.getParameters();

        if (params.length == 0) {
            return null;
        } else if (params.length == 1 && !TypeUtil.isPrimitive(params[0].getType())) {
            return args[0];
        } else {
            //instantiate input map
            HashMap<String, Object> input = new HashMap<String, Object>();
            //add args to input
            for (int i = 0; i < params.length; i++) {
                param = params[i];
                input.put(param.getName(), args[i]);
            }
            return input;
        }
    }

}
