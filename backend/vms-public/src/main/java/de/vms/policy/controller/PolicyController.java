package de.vms.policy.controller;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import de.vms.policy.model.Policy;
import de.vms.policy.model.dto.PolicySettings;
import de.vms.policy.service.PolicyService;

@RestController
@RequestMapping("/api/policy")
public class PolicyController {

    private PolicyService policyService;

    private ModelMapper modelMapper;

    public PolicyController(PolicyService policyService, ModelMapper modelMapper) {
        this.policyService = policyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public ResponseEntity<PolicySettings> getPolicy() {
        PolicySettings policySettings = policyService.getPolicy()
                .map(policy -> convertToDto(policy))
                .orElseGet(() -> convertToDto(policyService.createEmptyPolicy()));
        return ResponseEntity.ok().body(policySettings);
    }

    @PutMapping("/")
    public ResponseEntity<PolicySettings> updatePolicy(@RequestBody PolicySettings updatedPolicySettings) {
        Policy updatedPolicy = policyService.updatePolicy(convertToEntity(updatedPolicySettings),
                updatedPolicySettings.getPossibleCsrfHeaders());
        return ResponseEntity.ok(convertToDto(updatedPolicy));
    }

    private PolicySettings convertToDto(Policy policy) {
        PolicySettings settings = modelMapper.map(policy, PolicySettings.class);
        settings.setPossibleCsrfHeaders(policy.getCsrfCheckerConfig().getCsrfHeaders());
        return settings;
    }

    private Policy convertToEntity(PolicySettings policySettings) {
        return modelMapper.map(policySettings, Policy.class);
    }
}
