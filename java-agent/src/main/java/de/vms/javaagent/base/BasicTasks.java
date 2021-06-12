package de.vms.javaagent.base;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vms.javaagent.api.base.SimpleHttpClient;
import de.vms.javaagent.config.AgentConfiguration;

public class BasicTasks {

    private static final Logger LOG = LoggerFactory.getLogger(BasicTasks.class);

    private static Runnable createHealthCheckRunnable(AgentConfiguration agentConfiguration) {
        return () -> {
            SimpleHttpClient client = new SimpleHttpClient(agentConfiguration);
            //get configuration
            try {
                java.net.http.HttpResponse response = client.put(
                        "/api/internal/agents/" + agentConfiguration.getAgentKey() + "/health-check", "");
                if (response.statusCode() != HttpStatus.SC_NO_CONTENT) {
                    LOG.info("IAST-Agent has problems connecting to master server. Status Code: {}",
                            response.statusCode());
                }
            } catch (Exception e) {
                LOG.warn("IAST-Agent has problems connecting to master server");
            }
        };
    }

    public static void registerBasicTasks(AgentConfiguration agentConfiguration) {
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        LOG.info("register agent health check...");
        exec.scheduleAtFixedRate(createHealthCheckRunnable(agentConfiguration), 4, 4, TimeUnit.MINUTES);
    }
}
