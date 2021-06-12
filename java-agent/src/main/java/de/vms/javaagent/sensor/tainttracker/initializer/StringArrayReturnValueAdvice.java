package de.vms.javaagent.sensor.tainttracker.initializer;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public final class StringArrayReturnValueAdvice {
    public static final Logger LOG = LoggerFactory.getLogger(StringArrayReturnValueAdvice.class);
    
    private StringArrayReturnValueAdvice() {
        //not called
    }

    @Advice.OnMethodExit
    public static void after(@Advice.Origin Method m, @Advice.Return Object returnValue) {
        LOG.debug("{}.{} was called.", m.getDeclaringClass(), m.getName());
        
        if (returnValue instanceof String[]) {
            for (String item : (String[]) returnValue) {
                if (!item.isBlank()) {
                    TaintHelper.setTaint(item, true);
                }
            }
        }
    }
}
