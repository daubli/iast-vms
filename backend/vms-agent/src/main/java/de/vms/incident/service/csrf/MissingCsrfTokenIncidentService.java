package de.vms.incident.service.csrf;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.springframework.stereotype.Component;

import de.vms.agent.model.Agent;
import de.vms.agent.service.AgentService;
import de.vms.incident.model.Incident;
import de.vms.incident.model.IncidentType;
import de.vms.incident.persistence.IncidentRepository;
import de.vms.incident.service.IncidentService;
import de.vms.incident.stacktrace.StackTraceUtil;
import de.vms.vulnerability.model.Vulnerability;
import de.vms.vulnerability.model.VulnerabilityStatus;
import de.vms.vulnerability.service.VulnerabilityService;

@Component
public class MissingCsrfTokenIncidentService implements IncidentService {

    private IncidentRepository incidentRepository;

    private AgentService agentService;

    private VulnerabilityService vulnerabilityService;

    public MissingCsrfTokenIncidentService(IncidentRepository incidentRepository, AgentService agentService,
            VulnerabilityService vulnerabilityService) {
        this.incidentRepository = incidentRepository;
        this.agentService = agentService;
        this.vulnerabilityService = vulnerabilityService;
    }

    @Override
    public synchronized boolean handleIncident(String agentId, Incident newIncident, String applicationMainPackageNameOrNull) {
        List<Incident> missingCsrfTokenIncidents =
                incidentRepository.findIncidentsByIncidentType(IncidentType.MISSING_CSRF_TOKEN);

        if (missingCsrfTokenIncidents.size() == 0) {
            //persist incident entity
            createNewIncident(newIncident, agentId, applicationMainPackageNameOrNull);
            //create vulnerability
            vulnerabilityService.createNewVulnerabilityWithIncident(newIncident);
            return true;
        }

        //handle duplicates
        String requestURI = newIncident.getHttpRequestEntity().getRequestURI();

        boolean duplicateFound = missingCsrfTokenIncidents.stream()
                .anyMatch(item -> item.getHttpRequestEntity().getRequestURI().equals(requestURI));

        Vulnerability relevantVulnerability = missingCsrfTokenIncidents.get(0).getVulnerability();

        //
        if (!duplicateFound) {
            //ensure that vulnerability is open
            relevantVulnerability.setStatus(VulnerabilityStatus.OPEN);
            relevantVulnerability.setLastDetected(LocalDateTime.now(ZoneOffset.UTC));
            //add incident and persist entity
            vulnerabilityService.addIncidentToVulnerability(relevantVulnerability, newIncident);
            return true;
        } else {
            vulnerabilityService.updateDuplicateVulnerability(relevantVulnerability);
            return false;
        }
    }

    private void createNewIncident(Incident incidentEntity, String agentId, String applicationMainPackageName) {
        Agent agent = agentService.getAgentByIdentifier(agentId);
        incidentEntity.addToDetectedOnAgents(agent);
        incidentEntity.setRelevantPointInApplication(
                StackTraceUtil.getFirstOccuringLineOfPackageInStackTrace(incidentEntity.getStackTrace(),
                        applicationMainPackageName).orElse(null));
        incidentRepository.save(incidentEntity);
    }

    @Override
    public IncidentType getAssociatedIncidentType() {
        return IncidentType.MISSING_CSRF_TOKEN;
    }
}
