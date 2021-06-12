package de.vms.javaagent.sensor.tainttracker.transformer;

import java.util.List;

import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.matcher.ElementMatcher;

import de.vms.javaagent.sensor.tainttracker.config.MethodConfig;

public class MethodMatcher {
    public static ElementMatcher<? super MethodDescription> from(final List<MethodConfig> methodConfigs) {
        JunctionBuilder junctionBuilder = new JunctionBuilder();

        for (MethodConfig methodConfig : methodConfigs) {
            JunctionBuilder methodJunctionBuilder = new JunctionBuilder().withMethodName(methodConfig.getMethodName())
                    .withMethodArguments(methodConfig.getArguments())
                    .withReturnType(methodConfig.getByReturnType())
                    .withAnnotation(methodConfig.getByMethodAnnotation());

            junctionBuilder = junctionBuilder.withOrJunction(methodJunctionBuilder.build());
        }

        return junctionBuilder.build();
    }
}
