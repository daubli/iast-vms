package de.vms.javaagent.sensor.tainttracker.checker;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.asm.Advice.OnMethodEnter;

import de.vms.javaagent.reporting.IncidentReporter;
import de.vms.javaagent.reporting.model.SqlInjectionIncident;
import de.vms.javaagent.sensor.tainttracker.util.TaintHelper;

public class SqlAdvice {
    public static final Logger LOG = LoggerFactory.getLogger(SqlAdvice.class);

    @OnMethodEnter
    public static void intercept(@Advice.AllArguments Object[] allArguments, @Advice.Origin Method method,
            @Advice.Local("foundTaintBefore") Boolean foundTaintBefore) {
        LOG.debug("{}.{} was called.", method.getDeclaringClass(), method.getName());
        foundTaintBefore = false;
        if (allArguments.length > 0) {
            String query = allArguments[0].toString();

            Boolean isTainted = TaintHelper.getTaint(query);
            if (isTainted != null && isTainted) {
                SqlInjectionIncident incident = new SqlInjectionIncident(query);
                IncidentReporter.report(incident);
                foundTaintBefore = true;
                TaintHelper.setTaint(query, false);
            }
        }
    }

    @Advice.OnMethodExit
    public static void after(@Advice.AllArguments Object[] allArguments, @Advice.Origin Method method,
            @Advice.Local("foundTaintBefore") Boolean foundTaintBefore) {
        if (foundTaintBefore && allArguments.length > 0) {
            String query = allArguments[0].toString();
            TaintHelper.setTaint(query, true);
        }
    }
}
