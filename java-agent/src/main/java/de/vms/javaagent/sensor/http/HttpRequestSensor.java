package de.vms.javaagent.sensor.http;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.util.Set;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.Sensor;
import de.vms.javaagent.sensor.http.advice.HttpRequestAdvice;

public class HttpRequestSensor implements Sensor {

    public HttpRequestSensor() {}

    @Override
    public Set<AgentBuilder> buildAgents() {
        return Set.of(new AgentBuilder.Default().disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(AgentBuilder.TypeStrategy.Default.REDEFINE)
                .type(hasSuperType(named("javax.servlet.Servlet")).or(
                        hasSuperType(named("javax.servlet.GenericServlet"))))
                .transform((builder, typeDescription, classLoader, module) -> builder.visit(
                        Advice.to(HttpRequestAdvice.class)
                                .on(isProtected().and(named("service")).and(
                                        takesArgument(0,
                                                named("javax.servlet.http.HttpServletRequest"))).and(
                                        takesArgument(1,
                                                named("javax.servlet.http.HttpServletResponse")
                                        ))))));
    }

    @Override
    public void initialize() {
        // nothing to initialize
        HttpRequestAdvice.initializeStaticFields();
    }
}
