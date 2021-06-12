package de.vms.incident.service.sqli;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import de.vms.agent.model.Agent;
import de.vms.agent.service.AgentService;
import de.vms.incident.model.Incident;
import de.vms.incident.model.IncidentType;
import de.vms.incident.persistence.IncidentRepository;
import de.vms.incident.service.IncidentService;
import de.vms.incident.stacktrace.AdvancedLevenshteinDistancer;
import de.vms.incident.stacktrace.StackTraceUtil;
import de.vms.incident.stacktrace.service.StackTraceService;
import de.vms.vulnerability.model.Vulnerability;
import de.vms.vulnerability.model.VulnerabilityStatus;
import de.vms.vulnerability.service.VulnerabilityService;

@Component
public class SqlInjectionIncidentService implements IncidentService {

    public static final double MIN_SIMILARITY_FOR_DUPLICATE = 0.9;
    private StackTraceService stackTraceService;

    private VulnerabilityService vulnerabilityService;

    private AgentService agentService;

    private IncidentRepository incidentRepository;

    public SqlInjectionIncidentService(StackTraceService stackTraceService, VulnerabilityService vulnerabilityService,
            AgentService agentService, IncidentRepository incidentRepository) {
        this.stackTraceService = stackTraceService;
        this.vulnerabilityService = vulnerabilityService;
        this.agentService = agentService;
        this.incidentRepository = incidentRepository;
    }

    @Override
    public boolean handleIncident(String agentId, Incident newIncident, String applicationMainPackageNameOrNull) {
        List<Incident> otherIncidentsOfSameTypeAsNewIncident =
                incidentRepository.findIncidentsByIncidentType(newIncident.getIncidentType());

        if (otherIncidentsOfSameTypeAsNewIncident.size() == 0) {
            //persist incident entity
            createNewIncident(newIncident, agentId, applicationMainPackageNameOrNull);
            //create vulnerability
            vulnerabilityService.createNewVulnerabilityWithIncident(newIncident);
            return true;
        }

        //compare queries and application points

        //calculate levenshtein-distance

        if (newIncident.getStackTrace() != null
                && newIncident.getStackTrace().getStackOfCalls().size() > 0) {

            Map<String, Long> stackTraceFrameFrequency = stackTraceService.getStackTraceFrameFrequency();
            Long numberOfSqlInjectionIncidents =
                    incidentRepository.countIncidentByIncidentType(IncidentType.SQL_INJECTION);

            AdvancedLevenshteinDistancer levenshteinDistancer =
                    new AdvancedLevenshteinDistancer(newIncident.getStackTrace(), numberOfSqlInjectionIncidents,
                            stackTraceFrameFrequency);

            for (Incident incident : otherIncidentsOfSameTypeAsNewIncident) {
                double similarity =
                        levenshteinDistancer.calculateSimilarity(incident.getStackTrace());
                //This similarity may be more narrow. One may check the entry points in the application code, too
                if (similarity > MIN_SIMILARITY_FOR_DUPLICATE) {
                    System.out.println("incident duplicate found");
                    Vulnerability vulnerabilityByIncident = vulnerabilityService.getVulnerabilityByIncident(incident);
                    if (!vulnerabilityByIncident.getStatus().equals(VulnerabilityStatus.INVALID)) {
                        vulnerabilityService.updateDuplicateVulnerability(vulnerabilityByIncident);
                    }
                    return false;
                }
            }

            createNewIncident(newIncident, agentId, applicationMainPackageNameOrNull);
            //create new vulnerability
            vulnerabilityService.createNewVulnerabilityWithIncident(newIncident);
        } else {
            return false;
        }
        return true;
    }

    private void createNewIncident(Incident incidentEntity, String agentId, String applicationMainPackageNameOrNull) {
        Agent agent = agentService.getAgentByIdentifier(agentId);
        incidentEntity.addToDetectedOnAgents(agent);
        if (applicationMainPackageNameOrNull != null) {
            incidentEntity.setRelevantPointInApplication(
                    StackTraceUtil.getFirstOccuringLineOfPackageInStackTrace(incidentEntity.getStackTrace(),
                            applicationMainPackageNameOrNull).orElse(null));
        }
        incidentRepository.save(incidentEntity);
    }

    @Override
    public IncidentType getAssociatedIncidentType() {
        return IncidentType.SQL_INJECTION;
    }
}
