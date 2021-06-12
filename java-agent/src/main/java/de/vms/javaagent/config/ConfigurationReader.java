package de.vms.javaagent.config;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vms.javaagent.IASTAgent;

public final class ConfigurationReader {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationReader.class);

    private static final String AGENT_KEY_PROP = "agent-key";

    private static final String VMS_URL_PROP = "vms-url";


    private ConfigurationReader() {
        //not called
    }

    public static AgentConfiguration readAgentConfigurationFromProperties() throws IOException {
        Properties prop = new Properties();
        prop.load(IASTAgent.class.getClassLoader().getResourceAsStream("config.properties"));
        AgentConfiguration agentConfiguration = AgentConfiguration.getAgentConfiguration();
        agentConfiguration.setAgentKey(prop.getProperty(AGENT_KEY_PROP));
        agentConfiguration.setVmsURL(prop.getProperty(VMS_URL_PROP));
        return agentConfiguration;
    }
}
