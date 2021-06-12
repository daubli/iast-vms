package de.vms.javaagent.sensor.tainttracker.initializer;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public class MapReturnValueAdvice {
    public static final Logger LOG = LoggerFactory.getLogger(MapReturnValueAdvice.class);

    @Advice.OnMethodExit
    public static void after(@Advice.Origin Method m, @Advice.Return Object returnValue) {
        LOG.debug("{}.{} was called.", m.getDeclaringClass(), m.getName());

        if (returnValue instanceof Map) {
            Map<String, Object> returnMap = (Map) returnValue;
            taintMap(returnMap);
        }
    }

    public static void taintMap(Map<String, Object> returnValue) {
        for (Map.Entry<String, Object> entry : returnValue.entrySet()) {
            TaintHelper.setTaint(entry.getKey(), true);

            if (entry.getValue() instanceof Map) {
                taintMap((Map<String, Object>) entry.getValue());
            } else if (entry.getValue() instanceof String && !((String) entry.getValue()).isBlank()) {
                TaintHelper.setTaint(entry.getValue(), true);
            } else if (entry.getValue() instanceof Object[]) {
                taintArray((Object[]) entry.getValue());
            }
        }
    }

    public static void taintArray(Object[] arrayToTaint) {
        for (Object arrayItem : arrayToTaint) {
            if (arrayItem instanceof String && !((String) arrayItem).isBlank()) {
                TaintHelper.setTaint(arrayItem, true);
            } else if (arrayItem instanceof Map) {
                taintMap((Map<String, Object>) arrayItem);
            }
        }
    }
}
