package de.vms.javaagent.sensor.tainttracker.propagator;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.tainttracker.util.ReflectionUtil;
import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public class JSONObjectTaintSaverAdvice {
    public static final Logger LOG = LoggerFactory.getLogger(JSONObjectTaintSaverAdvice.class);
    @Advice.OnMethodExit
    public static void intercept(@Advice.AllArguments Object[] allArguments, @Advice.This Object constructedObject) {
        Boolean tainted = false;
        if (allArguments[0] != null && allArguments[0] instanceof String) {
            String arg = (String) allArguments[0];
            tainted = TaintHelper.getTaint(arg);
        }

        if (allArguments[0] != null && allArguments[0].getClass().getSimpleName().equals("JSONTokener")) {
            try {
                Field readerField = allArguments[0].getClass().getDeclaredField("reader");
                readerField.setAccessible(true);
                StringReader reader = (StringReader) readerField.get(allArguments[0]);
                Field strField = StringReader.class.getDeclaredField("str");
                strField.setAccessible(true);
                String str = (String) strField.get(reader);
                tainted = TaintHelper.getTaint(str);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (tainted) {
            //taint all key/value pairs of result
            try {
                Field mapField = constructedObject.getClass().getDeclaredField("map");
                mapField.setAccessible(true);
                Map map = (Map) mapField.get(constructedObject);

                for (Object key : map.keySet()) {
                    if (key instanceof String) {
                        Object value =
                                ReflectionUtil.invokeMethodOnObjectWithParam(map.getClass(), "get", Object.class, map,
                                        key);

                        if (value != null) {
                            TaintHelper.setTaint(key, true);

                            if (value instanceof String) {
                                TaintHelper.setTaint(value, true);
                            }
                        }
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
