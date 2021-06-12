package de.vms.javaagent.sensor.tainttracker.propagator;

import static net.bytebuddy.matcher.ElementMatchers.*;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

public class JSONObjectTaintSaver {
    private AgentBuilder agentBuilder;

    public JSONObjectTaintSaver() {
        agentBuilder = new AgentBuilder.Default().disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.RETRANSFORMATION)
                .type(named("org.json.JSONObject"))
                .transform((builder, typeDescription, classLoader, module) -> builder
                        .visit(Advice
                                .to(JSONObjectTaintSaverAdvice.class)
                                .on(isConstructor().and(takesArguments(String.class).or(
                                        takesArgument(0, nameContains("JSONTokener")))))));
    }

    public AgentBuilder getAgentBuilder() {
        return agentBuilder;
    }
}
