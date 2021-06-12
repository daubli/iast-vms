package de.vms.policy.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.vms.policy.model.Policy;

public interface PolicyRepository extends JpaRepository<Policy, UUID> {
    Policy findTopByIdNotNull();
}
