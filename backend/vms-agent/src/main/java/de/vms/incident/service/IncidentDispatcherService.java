package de.vms.incident.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import de.vms.incident.model.IncidentType;

@Service
public class IncidentDispatcherService {

    private List<IncidentService> incidentServices;

    private static final Map<IncidentType, IncidentService> incidentServiceCache = new HashMap<>();

    public IncidentDispatcherService(List<IncidentService> incidentServices) {
        this.incidentServices = incidentServices;
    }

    @PostConstruct
    public void initIncidentServiceCache() {
        for (IncidentService incidentService : incidentServices) {
            incidentServiceCache.put(incidentService.getAssociatedIncidentType(), incidentService);
        }
    }

    public IncidentService getIncidentService(IncidentType incidentType) {
        IncidentService incidentService = incidentServiceCache.get(incidentType);
        if (incidentService == null) {
            throw new RuntimeException("Unknown service type: " + incidentType.getType());
        }
        return incidentService;
    }
}
