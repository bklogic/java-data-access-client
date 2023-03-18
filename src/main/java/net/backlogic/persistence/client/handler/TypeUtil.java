package net.backlogic.persistence.client.handler;

import java.util.HashSet;

public class TypeUtil {
    private static final HashSet<Class<?>> primitiveTypes;

    static {
        primitiveTypes = new HashSet<Class<?>>();
        primitiveTypes.add(Boolean.class);
        primitiveTypes.add(Character.class);
        primitiveTypes.add(Byte.class);
        primitiveTypes.add(Short.class);
        primitiveTypes.add(Integer.class);
        primitiveTypes.add(Long.class);
        primitiveTypes.add(Float.class);
        primitiveTypes.add(Double.class);
        primitiveTypes.add(String.class);
        primitiveTypes.add(java.sql.Date.class);
        primitiveTypes.add(java.util.Date.class);
        primitiveTypes.add(java.util.Calendar.class);
    }

    /*
     * To check whether a parameter is a primitive type
     */
    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || primitiveTypes.contains(type);
    }


}
