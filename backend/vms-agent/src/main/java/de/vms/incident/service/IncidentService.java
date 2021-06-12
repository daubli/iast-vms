package de.vms.incident.service;

import de.vms.incident.model.Incident;
import de.vms.incident.model.IncidentType;

public interface IncidentService {

    boolean handleIncident(String agentId, Incident newIncident, String applicationMainPackageNameOrNull);

    IncidentType getAssociatedIncidentType();
}
