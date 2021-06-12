package de.vms.javaagent.sensor.tainttracker;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.tainttracker.checker.SqlAdvice;
import de.vms.javaagent.sensor.tainttracker.config.MethodConfig;
import de.vms.javaagent.sensor.tainttracker.config.TaintTrackerSensorConfig;
import de.vms.javaagent.sensor.tainttracker.initializer.ReturnValueAdvice;
import de.vms.javaagent.sensor.tainttracker.initializer.StringParameterAdvice;
import de.vms.javaagent.sensor.tainttracker.propagator.JSONObjectTaintSaver;
import de.vms.javaagent.sensor.tainttracker.propagator.TaintSaverAdvice;
import de.vms.javaagent.sensor.tainttracker.transformer.MethodMatcher;
import de.vms.javaagent.sensor.tainttracker.transformer.TypeMatcher;
import de.vms.javaagent.sensor.Sensor;

public class TaintTrackerSensor implements Sensor {

    private TaintTrackerSensorConfig taintTrackerSensorConfig;

    private static final Logger LOG = LoggerFactory.getLogger(TaintTrackerSensor.class);

    public TaintTrackerSensor(TaintTrackerSensorConfig taintTrackerSensorConfig) {
        this.taintTrackerSensorConfig = taintTrackerSensorConfig;
    }

    @Override
    public void initialize() {
        /*
            Use string builder to concat strings. This is a performance drawback, but
            dynamic string concat turned out to be hard to instrument...
         */

        System.setProperty("java.lang.invoke.stringConcat", "BC_SB");
    }

    @Override
    public final Set<AgentBuilder> buildAgents() {
        //logic
        List<MethodConfig> sources = Arrays.asList(taintTrackerSensorConfig.getSources());

        List<MethodConfig> methodsWithParameterTainting = new ArrayList<>();
        List<MethodConfig> methodsWithReturnValueTainting = new ArrayList<>();

        for (MethodConfig source : sources) {
            switch (source.getTaint()) {
                case PARAMETER:
                    methodsWithParameterTainting.add(source);
                    break;
                case RETURN_VALUE:
                    methodsWithReturnValueTainting.add(source);
                    break;
            }
        }

        List<MethodConfig> taintThrough = Arrays.asList(taintTrackerSensorConfig.getTaintThrough());

        List<MethodConfig> sinks = Arrays.asList(taintTrackerSensorConfig.getSinks());

        Set<AgentBuilder> agents = new HashSet<>();

        //sources
        agents.add(buildAgentBuilder(methodsWithParameterTainting, StringParameterAdvice.class));
        agents.add(buildAgentBuilder(methodsWithReturnValueTainting, ReturnValueAdvice.class));

        //taintThrough
        agents.add(buildAgentBuilder(taintThrough, TaintSaverAdvice.class));

        //sinks
        agents.add(buildAgentBuilder(sinks, SqlAdvice.class));

        //specific helper agents
        agents.add(new JSONObjectTaintSaver().getAgentBuilder());

        return agents;
    }

    private AgentBuilder buildAgentBuilder(List<MethodConfig> methods, Class adviceClass) {
        return new AgentBuilder.Default().disableClassFormatChanges()
                .with(AgentBuilder.RedefinitionStrategy.REDEFINITION)
                .with(AgentBuilder.TypeStrategy.Default.REDEFINE)
                .type(TypeMatcher.from(methods))
                .transform((builder, typeDescription, classLoader, module) -> builder.visit(Advice.to(
                        adviceClass).on(
                        MethodMatcher.from(methods)
                )));
    }
}
