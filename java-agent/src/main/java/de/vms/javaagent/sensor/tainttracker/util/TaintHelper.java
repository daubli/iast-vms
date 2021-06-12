package de.vms.javaagent.sensor.tainttracker.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaintHelper {
    private static Logger LOG = LoggerFactory.getLogger(TaintHelper.class);

    public static void setTaint(Object objectToTaint, boolean taintValue) {
        Method setTaintMethod = null;
        try {
            setTaintMethod = String.class.getMethod("setTaint", new Class[] { boolean.class });
            setTaintMethod.invoke(objectToTaint, new Object[] { taintValue });
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static Boolean getTaint(Object objectToCheck) {
        try {
            Method getTaintMethod = String.class.getMethod("getTaint");
            Object returnValue = getTaintMethod.invoke(objectToCheck);
            if (returnValue instanceof Boolean) {
                return (Boolean) returnValue;
            } else {
                return null;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            LOG.error("{}", e.getCause().getLocalizedMessage());
            e.printStackTrace();
            return null;
        }
    }
}
