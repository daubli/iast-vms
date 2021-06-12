package de.vms.javaagent.sensor.tainttracker.transformer;

import java.util.List;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

import de.vms.javaagent.sensor.tainttracker.config.MethodConfig;

public class TypeMatcher {
    public static ElementMatcher<? super TypeDescription> from(final List<MethodConfig> methodConfigs) {
        JunctionBuilder junctionBuilder = new JunctionBuilder();

        for (MethodConfig methodConfig : methodConfigs) {
            JunctionBuilder typeJunctionBuilder = new JunctionBuilder().withClassName(methodConfig.getByClass())
                    .withSuperTypeName(methodConfig.getBySuperType())
                    .withAnnotation(methodConfig.getByClassAnnotation());

            junctionBuilder = junctionBuilder.withOrJunction(typeJunctionBuilder.build());
        }

        return junctionBuilder.build();
    }

}
