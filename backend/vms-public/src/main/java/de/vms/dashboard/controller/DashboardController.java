package de.vms.dashboard.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.vms.agent.service.AgentService;
import de.vms.dashboard.model.dto.DashboardDto;
import de.vms.gate.service.GateStatusService;
import de.vms.vulnerability.model.Vulnerability;
import de.vms.vulnerability.model.dto.VulnerabilityDto;
import de.vms.vulnerability.service.ScoreService;
import de.vms.vulnerability.service.VulnerabilityService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    VulnerabilityService vulnerabilityService;

    AgentService agentService;

    ScoreService scoreService;

    GateStatusService gateStatusService;

    ModelMapper modelMapper;

    
    public DashboardController(VulnerabilityService vulnerabilityService, AgentService agentService,
            ScoreService scoreService, GateStatusService gateStatusService, ModelMapper modelMapper) {
        this.vulnerabilityService = vulnerabilityService;
        this.agentService = agentService;
        this.scoreService = scoreService;
        this.gateStatusService = gateStatusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<DashboardDto> getDashboard() {
        List<Vulnerability> vulEntities = vulnerabilityService.getTenLatestOpenVulnerabilities();
        List<VulnerabilityDto> tenLatestVulnerabilities =
                vulEntities.stream().map(entity -> modelMapper.map(entity, VulnerabilityDto.class)).collect(
                        Collectors.toList());

        Long numberOfAllAgents = agentService.getNumberOfAllAgents();
        Long numberOfActiveAgents = agentService.getNumberOfActiveAgents();

        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setAgentsOnline(numberOfActiveAgents);
        dashboardDto.setAgentsRegistered(numberOfAllAgents);
        dashboardDto.setLatestVulnerabilities(tenLatestVulnerabilities);
        dashboardDto.setScore(scoreService.getOverallVulnerabilityScore());
        dashboardDto.setGateHealth(gateStatusService.getGateHealth().getValue());
        return ResponseEntity.ok(dashboardDto);
    }
}
