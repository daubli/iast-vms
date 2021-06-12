package de.vms.javaagent.config;

public class AgentConfiguration {
    private String agentKey;
    private String vmsURL;

    private static AgentConfiguration agentConfiguration;

    public static synchronized AgentConfiguration getAgentConfiguration() {
        if (agentConfiguration == null) {
            agentConfiguration = new AgentConfiguration();
        }
        return agentConfiguration;
    }

    private AgentConfiguration() {
    }

    public String getAgentKey() {
        return agentKey;
    }

    public String getVmsURL() {
        return vmsURL;
    }

    public void setAgentKey(String agentKey) {
        this.agentKey = agentKey;
    }

    public void setVmsURL(String vmsURL) {
        this.vmsURL = vmsURL;
    }
}
