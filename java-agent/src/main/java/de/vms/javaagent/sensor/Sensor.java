package de.vms.javaagent.sensor;

import java.util.Set;

import net.bytebuddy.agent.builder.AgentBuilder;

public interface Sensor {
     Set<AgentBuilder> buildAgents();

     void initialize();
}
