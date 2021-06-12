package de.vms.gate.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.vms.gate.model.GateCondition;

public interface GateConditionRepository extends JpaRepository<GateCondition, UUID> {
}
