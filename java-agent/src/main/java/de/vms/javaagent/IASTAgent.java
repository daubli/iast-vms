package de.vms.javaagent;

import java.lang.instrument.Instrumentation;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.agent.builder.AgentBuilder;

import de.vms.javaagent.base.BasicTasks;
import de.vms.javaagent.config.AgentConfiguration;
import de.vms.javaagent.config.ConfigurationReader;
import de.vms.javaagent.config.SensorConfiguration;
import de.vms.javaagent.config.StartConfigLoader;
import de.vms.javaagent.reporting.IncidentReporter;
import de.vms.javaagent.sensor.IASTSensorFactory;
import de.vms.javaagent.sensor.Sensor;
import de.vms.javaagent.sensor.application.SpringFrameworkSensor;
import de.vms.javaagent.sensor.http.HttpRequestSensor;

public final class IASTAgent {

    private static final Logger LOG = LoggerFactory.getLogger(IASTAgent.class);

    private IASTAgent() {
        //not called
    }

    /*
        Instrument classes when they are loaded into the JVM
     */
    public static void premain(final String agentArguments, Instrumentation instrumentation) throws Exception {
        install(instrumentation);
    }

    private static void install(Instrumentation instrumentation)
            throws Exception {
        System.setProperty("java.lang.invoke.stringConcat", "BC_SB");
        System.out.println();
        System.out.println("Initialize startup of IAST Agent...");

        AgentConfiguration agentConfiguration;
        List<SensorConfiguration> sensorConfiguration;

        try {
            agentConfiguration = ConfigurationReader.readAgentConfigurationFromProperties();
            StartConfigLoader startConfigLoader = new StartConfigLoader(agentConfiguration);
            sensorConfiguration = startConfigLoader.fetchStartConfiguration();
            IncidentReporter.initializeReporter(agentConfiguration);
        } catch (Exception e) {
            LOG.error("Cannot initialize the agent. Starting without agent. Failed with Exception {}", e);
            return;
        }

        Sensor applicationSensor = new SpringFrameworkSensor();
        applicationSensor.initialize();
        Set<AgentBuilder> applicationAgents = applicationSensor.buildAgents();
        applicationAgents.forEach(agent -> agent.installOn(instrumentation));

        //register http request agent
        Sensor httpRequestSensor = new HttpRequestSensor();
        httpRequestSensor.initialize();
        Set<AgentBuilder> baseAgents = httpRequestSensor.buildAgents();
        baseAgents.forEach(agent -> agent.installOn(instrumentation));
        
        for (SensorConfiguration config : sensorConfiguration) {
            Sensor sensorOrNull = IASTSensorFactory.createSensor(config);

            if (sensorOrNull != null) {
                sensorOrNull.initialize();
                Set<AgentBuilder> agents = sensorOrNull.buildAgents();
                agents.forEach(agent -> agent.installOn(instrumentation));
            }
            LOG.info("Module {} initialized.", config.getModule());
        }

        BasicTasks.registerBasicTasks(agentConfiguration);

        LOG.info("IAST Agent initialized.");
    }
}
