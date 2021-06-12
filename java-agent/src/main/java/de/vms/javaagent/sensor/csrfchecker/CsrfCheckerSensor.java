package de.vms.javaagent.sensor.csrfchecker;

import java.util.Collections;
import java.util.Set;

import net.bytebuddy.agent.builder.AgentBuilder;

import de.vms.javaagent.sensor.Sensor;
import de.vms.javaagent.sensor.csrfchecker.config.CsrfCheckerSensorConfig;
import de.vms.javaagent.sensor.csrfchecker.request.CsrfCheckerAdvice;
import de.vms.javaagent.sensor.http.HttpRequestStorage;

public class CsrfCheckerSensor implements Sensor {
    private final CsrfCheckerSensorConfig csrfSensorConfig;

    @Override
    public Set<AgentBuilder> buildAgents() {
        return Collections.emptySet();
    }

    @Override
    public void initialize() {
         //register advices on httpRequestStorage
        CsrfCheckerAdvice csrfCheckerAdvice = new CsrfCheckerAdvice(this.csrfSensorConfig);
        HttpRequestStorage.getInstance().registerRequestAdvice(csrfCheckerAdvice);
    }

    public CsrfCheckerSensor(CsrfCheckerSensorConfig csrfCheckerSensorConfig) {
        this.csrfSensorConfig = csrfCheckerSensorConfig;
    }
}
