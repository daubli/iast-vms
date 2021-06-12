package de.vms.javaagent.sensor.tainttracker.initializer;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public class CookieReturnValueAdvice {
    public static final String COOKIE_CLASS_IDENTIFIER = "javax.servlet.http.Cookie";

    public static final Logger LOG = LoggerFactory.getLogger(CookieReturnValueAdvice.class);

    @Advice.OnMethodExit
    public static void after(@Advice.Origin Method m,
            @Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Object returnValue) {
        try {
            Class cookieClass =
                    Class.forName("javax.servlet.http.Cookie", false, Thread.currentThread().getContextClassLoader());
            Method getValueMethod = cookieClass.getMethod("getValue");
            Method setValueMethod = cookieClass.getMethod("setValue", String.class);
            Field nameField = cookieClass.getDeclaredField("name");
            nameField.setAccessible(true);

            for (Object cookie : (Object[]) returnValue) {
                String value = (String) getValueMethod.invoke(cookie);
                TaintHelper.setTaint(value, true);
                setValueMethod.invoke(cookie, value);

                String name = (String) nameField.get(cookie);
                TaintHelper.setTaint(name, true);
                nameField.set(cookie, name);
            }
        } catch (ClassNotFoundException e) {
            LOG.warn("Could not find {}", COOKIE_CLASS_IDENTIFIER);
        } catch (NoSuchMethodException e) {
            LOG.warn("Could not find method: {}", e.getMessage());
        } catch (IllegalAccessException e) {
            LOG.warn("Illegal Access: {}", e.getMessage());
        } catch (InvocationTargetException e) {
            LOG.warn("Cannot invoke: {}", e.getMessage());
        } catch (NoSuchFieldException e) {
            LOG.warn("Cannot find field: {}", e.getMessage());
        }
    }
}
