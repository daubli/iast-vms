package de.vms.dashboard.model.dto;

import java.util.List;

import de.vms.vulnerability.model.dto.VulnerabilityDto;

public class DashboardDto {
    private List<VulnerabilityDto> latestVulnerabilities;

    private Long agentsOnline;

    private Long agentsRegistered;

    private String score;

    private String gateHealth;

    public List<VulnerabilityDto> getLatestVulnerabilities() {
        return latestVulnerabilities;
    }

    public void setLatestVulnerabilities(
            List<VulnerabilityDto> latestVulnerabilities) {
        this.latestVulnerabilities = latestVulnerabilities;
    }

    public Long getAgentsOnline() {
        return agentsOnline;
    }

    public void setAgentsOnline(Long agentsOnline) {
        this.agentsOnline = agentsOnline;
    }

    public Long getAgentsRegistered() {
        return agentsRegistered;
    }

    public void setAgentsRegistered(Long agentsRegistered) {
        this.agentsRegistered = agentsRegistered;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getGateHealth() {
        return gateHealth;
    }

    public void setGateHealth(String gateHealth) {
        this.gateHealth = gateHealth;
    }
}
