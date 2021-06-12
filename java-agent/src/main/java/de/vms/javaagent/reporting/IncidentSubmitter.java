package de.vms.javaagent.reporting;

import com.google.gson.Gson;

import de.vms.javaagent.api.base.SimpleHttpClient;
import de.vms.javaagent.api.requesthandling.JsonSerializerFactory;
import de.vms.javaagent.config.AgentConfiguration;
import de.vms.javaagent.reporting.model.Incident;

public class IncidentSubmitter {
    private static IncidentSubmitter instance;

    private SimpleHttpClient client;

    private IncidentSubmitter() {
    }

    public static synchronized IncidentSubmitter getInstance () {
        if (IncidentSubmitter.instance == null) {
            IncidentSubmitter.instance = new IncidentSubmitter ();
        }
        return IncidentSubmitter.instance;
    }

    protected void submit(Incident incident) {
        Gson gson = JsonSerializerFactory.create();
        String data = gson.toJson(incident);

        try {
            client.postAsync("/api/internal/incident/", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initializeSubmitter(AgentConfiguration agentConfiguration) {
        client = new SimpleHttpClient(agentConfiguration);
    }
}
