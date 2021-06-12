package de.vms.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.vms.policy.model.dto.Configuration;
import de.vms.config.service.AgentConfigurationService;

@RestController
@RequestMapping("/api/internal/config")
public class AgentConfigurationController {

    private AgentConfigurationService agentConfigurationService;

    public AgentConfigurationController(AgentConfigurationService agentConfigurationService) {
        this.agentConfigurationService = agentConfigurationService;
    }

    @GetMapping("/")
    public ResponseEntity<Configuration> getAgentConfiguration(@RequestHeader("X-VMS-AGENT") String agentId) {
        Configuration configuration = new Configuration();

        if (agentConfigurationService.isTaintTrackerActivated()) {
            configuration.setTaintTrackerConfig(agentConfigurationService.getTaintTrackerConfig());
        }

        if (agentConfigurationService.isCsrfCheckActivated()) {
            configuration.setCsrfCheckerConfig(agentConfigurationService.getCsrfCheckerConfig());
        }

        return ResponseEntity.ok(configuration);
    }
}
