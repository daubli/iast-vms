package de.vms.policy.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.vms.policy.model.CsrfCheckerConfig;
import de.vms.policy.model.TaintTrackerConfig;
import de.vms.policy.model.Policy;
import de.vms.policy.persistence.CsrfCheckerConfigRepository;
import de.vms.policy.persistence.PolicyRepository;
import de.vms.policy.persistence.TaintTrackerConfigRepository;

@Service
public class PolicyService {

    private PolicyRepository policyRepository;

    private CsrfCheckerConfigRepository csrfCheckerConfigRepository;

    private TaintTrackerConfigRepository taintTrackerConfigRepository;

    public PolicyService(PolicyRepository policyRepository, CsrfCheckerConfigRepository csrfCheckerConfigRepository,
            TaintTrackerConfigRepository taintTrackerConfigRepository) {
        this.policyRepository = policyRepository;
        this.csrfCheckerConfigRepository = csrfCheckerConfigRepository;
        this.taintTrackerConfigRepository = taintTrackerConfigRepository;
    }

    public Optional<Policy> getPolicy() {
        List<Policy> allSettings = policyRepository.findAll();
        //There should be not more than one
        assert allSettings.size() < 2;
        if (allSettings != null && allSettings.size() == 1) {
            return Optional.of(allSettings.get(0));
        } else {
            return Optional.empty();
        }
    }

    public Policy createEmptyPolicy() {
        Policy policy = new Policy();
        CsrfCheckerConfig csrfCheckerConfig = new CsrfCheckerConfig();
        csrfCheckerConfigRepository.save(csrfCheckerConfig);
        TaintTrackerConfig taintTrackerConfig = new TaintTrackerConfig();
        taintTrackerConfigRepository.save(taintTrackerConfig);
        policy.setCsrfCheckerConfig(csrfCheckerConfig);
        policy.setTaintTrackerConfig(taintTrackerConfig);
        return policyRepository.save(policy);
    }

    public Policy updatePolicy(Policy policy, Set<String> possibleCsrfHeaders) {
        Policy policyEntity = policyRepository.getOne(policy.getId());
        CsrfCheckerConfig csrfCheckerConfig = policyEntity.getCsrfCheckerConfig();
        if (csrfCheckerConfig != null) {
            csrfCheckerConfig.setCsrfHeaders(possibleCsrfHeaders);
        } else {
            csrfCheckerConfig = new CsrfCheckerConfig();
            csrfCheckerConfig.setCsrfHeaders(possibleCsrfHeaders);
        }
        csrfCheckerConfigRepository.save(csrfCheckerConfig);
        
        policy.setCsrfCheckerConfig(csrfCheckerConfig);
        return policyRepository.save(policy);
    }
}
