package de.vms.gate.service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import de.vms.gate.model.GateCondition;
import de.vms.gate.model.GateConditionMetric;
import de.vms.gate.model.GateHealth;
import de.vms.vulnerability.model.Severity;
import de.vms.vulnerability.service.ScoreService;
import de.vms.vulnerability.service.VulnerabilityService;

@Service
public class GateStatusService {

    private GateConditionService gateConditionService;

    private VulnerabilityService vulnerabilityService;

    private ScoreService scoreService;

    public GateStatusService(GateConditionService gateConditionService, VulnerabilityService vulnerabilityService,
            ScoreService scoreService) {
        this.gateConditionService = gateConditionService;
        this.vulnerabilityService = vulnerabilityService;
        this.scoreService = scoreService;
    }

    public GateHealth getGateHealth() {
        List<GateCondition> allGateConditions = gateConditionService.getAllGateConditions();
        boolean matchedIndicator = true;

        Set<GateConditionMetric> numberOfVulnerabilityMetrics = Arrays.stream(GateConditionMetric.values())
                .filter(metric -> !metric.equals(GateConditionMetric.RATING)).collect(Collectors.toSet());

        for (GateConditionMetric metric : numberOfVulnerabilityMetrics) {
            List<GateCondition> relevantGateConditions = allGateConditions.stream()
                    .filter(condition -> condition.getMetric().equals(metric)).collect(
                            Collectors.toList());
            matchedIndicator =
                    checkNumberOfVulnerabilitiesMetric(relevantGateConditions, metric, matchedIndicator);
        }

        if (matchedIndicator) {
            char overallVulnerabilityScore = scoreService.getOverallVulnerabilityScore().charAt(0);
            Set<GateCondition> ratingConditionStreamSet = allGateConditions.stream()
                    .filter(condition -> condition.getMetric().equals(GateConditionMetric.RATING)).collect(
                            Collectors.toSet());

            for (GateCondition condition : ratingConditionStreamSet) {
                char value = condition.getValue().charAt(0);
                switch (condition.getOperator()) {
                    case BETTER_THAN:
                        value = value == 'A' ? 'B' : value; //you cannot be better than A
                        matchedIndicator = overallVulnerabilityScore < value;
                        break;
                    case WORSE_THAN:
                        matchedIndicator = overallVulnerabilityScore > value;
                        break;
                    default:
                        matchedIndicator = true;
                }
                if (!matchedIndicator) {
                    break;
                }
            }
        }

        if (matchedIndicator) {
            return GateHealth.PASSING;
        } else {
            return GateHealth.NOT_PASSING;
        }
    }

    private boolean checkNumberOfVulnerabilitiesMetric(List<GateCondition> gateConditionList,
            GateConditionMetric metric, boolean matchedIndicator) {
        if (!matchedIndicator) {
            return false;
        }

        if (!gateConditionList.isEmpty()) {

            final Long numberOfVulnerabilities;

            switch (metric) {
                case NUMBER_OF_HIGH_VUL:
                    numberOfVulnerabilities = vulnerabilityService.getNumberOfVulnerabilitiesBySeverity(Severity.HIGH);
                    break;
                case NUMBER_OF_MOD_VUL:
                    numberOfVulnerabilities =
                            vulnerabilityService.getNumberOfVulnerabilitiesBySeverity(Severity.MODERATE);
                    break;
                case NUMBER_OF_LOW_VUL:
                    numberOfVulnerabilities = vulnerabilityService.getNumberOfVulnerabilitiesBySeverity(Severity.LOW);
                    break;
                default:
                    numberOfVulnerabilities = 0l;
            }

            for (GateCondition condition : gateConditionList) {
                Long value = Long.valueOf(condition.getValue());
                switch (condition.getOperator()) {
                    case LESS_THAN:
                        matchedIndicator = numberOfVulnerabilities < value;
                        break;
                    case GREATER_THAN:
                        matchedIndicator = numberOfVulnerabilities > value;
                        break;
                    default:
                        matchedIndicator = true;
                }
                if (!matchedIndicator) {
                    break;
                }
            }
        }
        return matchedIndicator;
    }
}
