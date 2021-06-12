package de.vms.javaagent.sensor.tainttracker.initializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public class BodyTaintInitializer {

    public static void taintObject(Object objectToTaint) {
        //search all java.lang.String-members in the given object and taint them

        if (objectToTaint instanceof String) {
            TaintHelper.setTaint(objectToTaint, true);
            return;
        } else if (objectToTaint instanceof List) {
            for (Object o : (List) objectToTaint) {
                taintObject(o);
            }
        } else if (objectToTaint instanceof Map) {
            for (Map.Entry entry : ((Map<?, ?>) objectToTaint).entrySet()) {
                taintObject(entry.getKey());
                taintObject(entry.getValue());
            }
        }

        Class typeOfObjectToTaint = objectToTaint.getClass();

        Field[] fields = typeOfObjectToTaint.getDeclaredFields();

        for (Field f : fields) {
            if (f.getType().isPrimitive() || Modifier.isFinal(f.getModifiers())) {
                continue;
            }

            f.setAccessible(true);
            if (f.getType().equals(String.class)) {
                try {
                    Object relevantObject = f.get(objectToTaint);
                    if (relevantObject != null) {
                        TaintHelper.setTaint(relevantObject, true);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            } else {
                if (Collection.class.isAssignableFrom(f.getType())){
                    try {
                        Collection collectionObjects = (Collection) f.get(objectToTaint);
                        for (Object collectionObject : collectionObjects) {
                            taintObject(collectionObject);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else if (Map.class.isAssignableFrom(f.getType())) {
                    try {
                        Map<Object, Object> mapObject = (Map) f.get(objectToTaint);
                        for (Map.Entry entry : mapObject.entrySet()) {
                            taintObject(entry.getKey());
                            taintObject(entry.getValue());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Object nestedObject = f.get(objectToTaint);
                        if (nestedObject != null) {
                            taintObject(nestedObject);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
