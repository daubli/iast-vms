package de.vms.javaagent.sensor.tainttracker.initializer;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;

public class ReturnValueAdvice {
    public static final Logger LOG = LoggerFactory.getLogger(ReturnValueAdvice.class);

    public static final String COOKIE_CLASS_IDENTIFIER_ARRAY = "javax.servlet.http.Cookie[]";

    @Advice.OnMethodExit
    public static void after(@Advice.Origin Method m, @Advice.Return Object returnValue) {
        LOG.debug("{}.{} was called.", m.getDeclaringClass(), m.getName());
        
        if (returnValue instanceof String) {
            StringReturnValueAdvice.after(m, returnValue);
        } else if (returnValue instanceof Map) {
            MapReturnValueAdvice.after(m, returnValue);
        } else if (returnValue instanceof String[]) {
            StringArrayReturnValueAdvice.after(m, returnValue);
        } else if (returnValue instanceof Enumeration) {
            EnumerationReturnValueAdvice.after(m, (Enumeration) returnValue);
        } else if (returnValue != null && COOKIE_CLASS_IDENTIFIER_ARRAY.equals(returnValue.getClass().getCanonicalName())) {
            CookieReturnValueAdvice.after(m, returnValue);
        } else {
            if (returnValue != null) {
                LOG.warn("Advice was called but return value of method {} is not supported. Type is {}", m.getName(),
                        returnValue.getClass().getName());
                new Exception().printStackTrace(System.out);
            }
        }
    }
}
