package de.vms.javaagent.sensor.application;

import static net.bytebuddy.matcher.ElementMatchers.*;

import java.util.Set;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.Sensor;
import de.vms.javaagent.sensor.application.advice.SpringAdvice;

public class SpringFrameworkSensor implements Sensor {
    @Override
    public Set<AgentBuilder> buildAgents() {
        return Set.of(new AgentBuilder.Default().disableClassFormatChanges()
                .with(AgentBuilder.TypeStrategy.Default.DECORATE)
                .type(nameEndsWith("org.springframework.boot.loader.Launcher"))
                .transform((builder, typeDescription, classLoader, module) -> builder.visit(
                        Advice.to(SpringAdvice.class)
                                .on(named("launch").and(takesArguments(3))))));
    }

    @Override
    public void initialize() {
        //empty
    }
}
