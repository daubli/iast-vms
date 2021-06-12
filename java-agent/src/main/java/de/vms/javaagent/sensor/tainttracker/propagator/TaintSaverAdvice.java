package de.vms.javaagent.sensor.tainttracker.propagator;

import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public class TaintSaverAdvice {

    @Advice.OnMethodExit
    public static void intercept(@Advice.AllArguments Object[] allArguments, @Advice.Return Object returnObject) {
        if (allArguments.length == 1) {

            if (allArguments[0] instanceof String && returnObject != null && returnObject instanceof String
                    && allArguments[0].equals(returnObject)) {
                String arg = (String) allArguments[0];
                String returnValue = (String) returnObject;
                Boolean tainted = TaintHelper.getTaint(arg);
                if (tainted) {
                    TaintHelper.setTaint(returnValue, true);
                }
            }
        }
    }
}
