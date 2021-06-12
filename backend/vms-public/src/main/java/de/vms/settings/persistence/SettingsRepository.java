package de.vms.settings.persistence;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.vms.settings.model.Settings;

public interface SettingsRepository extends JpaRepository<Settings, UUID> {
    Settings findTopByIdNotNull();

}
