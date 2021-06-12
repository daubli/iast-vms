package de.vms.javaagent.sensor.tainttracker.initializer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.Advice.OnMethodEnter;

import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public final class StringParameterAdvice {
    public static final Logger LOG = LoggerFactory.getLogger(StringParameterAdvice.class);

    private StringParameterAdvice() {
        //not called
    }

    @OnMethodEnter
    public static void before(@Advice.Origin Method m, @Advice.AllArguments Object[] allArguments) {
        LOG.debug("{}.{} was called.", m.getDeclaringClass(), m.getName());

        for (int i = 0; i < allArguments.length; i++) {
            Object argument = allArguments[i];

            if (argument == null) {
                continue;
            }

            if (argument instanceof String && !((String) argument).isBlank()) {
                TaintHelper.setTaint(argument, true);
            } else if (m.getParameterAnnotations().length != 0){
                Annotation[] parameterAnnotations = m.getParameterAnnotations()[i];
                for (Annotation annotation : parameterAnnotations) {
                    if ("org.springframework.web.bind.annotation.RequestBody".equals(
                            annotation.annotationType().getName())) {
                        BodyTaintInitializer.taintObject(argument);
                    }
                }
            }
        }
    }
}
