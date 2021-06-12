package de.vms.incident.persistence;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.vms.incident.model.Incident;
import de.vms.incident.model.IncidentType;

public interface IncidentRepository extends JpaRepository<Incident, UUID> {
    @Transactional
    List<Incident> findIncidentsByIncidentType(IncidentType incidentType);

    Long countIncidentByIncidentType(IncidentType incidentType);
}
