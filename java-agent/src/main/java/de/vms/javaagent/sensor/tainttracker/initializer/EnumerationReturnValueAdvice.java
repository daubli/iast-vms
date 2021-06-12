package de.vms.javaagent.sensor.tainttracker.initializer;

import java.lang.reflect.Method;
import java.util.Enumeration;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bytecode.assign.Assigner;

import de.vms.javaagent.sensor.tainttracker.initializer.clone.EnumerationClone;

public class EnumerationReturnValueAdvice {
    @Advice.OnMethodExit
    public static void after(@Advice.Origin Method m,
            @Advice.Return(readOnly = false, typing = Assigner.Typing.DYNAMIC) Enumeration returnValue) {
        if (returnValue instanceof Enumeration) {
            returnValue = new EnumerationClone(returnValue);
        }
    }
}
