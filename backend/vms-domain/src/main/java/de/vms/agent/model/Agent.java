package de.vms.agent.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

import de.vms.incident.model.Incident;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String identifier;

    private LocalDateTime lastSeen;

    @ManyToMany(mappedBy = "detectedOnAgents")
    private Set<Incident> incidents;

    public Agent() {
        this.incidents = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String description) {
        this.identifier = description;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Set<Incident> getIncidents() {
        return incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        this.incidents = incidents;
    }
}
