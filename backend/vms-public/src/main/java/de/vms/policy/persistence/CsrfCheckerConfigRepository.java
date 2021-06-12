package de.vms.policy.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.vms.policy.model.CsrfCheckerConfig;

public interface CsrfCheckerConfigRepository extends JpaRepository<CsrfCheckerConfig, UUID> {
}
