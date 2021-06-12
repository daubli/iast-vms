package de.vms.javaagent.sensor.tainttracker.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionUtil {


    private static Method getMethodForClass(Class clazz, String methodName, Class<?> paramType) throws NoSuchMethodException {
        if (paramType == null) {
            return clazz.getMethod(methodName);
        } else {
            return clazz.getMethod(methodName, paramType);
        }
    }

    private static Method getMethodForClass(Class clazz, String methodName) throws NoSuchMethodException {
        return getMethodForClass(clazz, methodName, null);
    }

    public static Object invokeMethodOnObjectWithParam(Class clazz, String methodName, Class<?> paramType,
            Object object, Object param) {
        try {
            if (paramType != null && param != null) {
                return getMethodForClass(clazz, methodName, paramType).invoke(object, param);
            } else if (paramType == null && param == null) {
                return getMethodForClass(clazz, methodName).invoke(object);
            } else {
                throw new UnsupportedOperationException();
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
