package de.vms.gate.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import de.vms.gate.model.GateCondition;
import de.vms.gate.persistence.GateConditionRepository;

@Service
public class GateConditionService {

    GateConditionRepository gateConditionRepository;

    public GateConditionService(GateConditionRepository gateConditionRepository) {
        this.gateConditionRepository = gateConditionRepository;
    }

    public List<GateCondition> getAllGateConditions() {
        return gateConditionRepository.findAll();
    }

    public GateCondition createCondition(GateCondition condition) {
        return gateConditionRepository.save(condition);
    }

    public GateCondition updateCondition(GateCondition condition) {
        return gateConditionRepository.save(condition);
    }

    public void deleteCondition(UUID conditionId) {
        gateConditionRepository.deleteById(conditionId);
    }
}
