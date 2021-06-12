package de.vms.incident.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

import de.vms.agent.model.Agent;
import de.vms.vulnerability.model.Vulnerability;

@Entity
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    private LocalDateTime timestamp;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "stacktrace_id", referencedColumnName = "id")
    private StackTraceEntity stackTrace;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "http_request_id", referencedColumnName = "id")
    private HttpRequestEntity httpRequestEntity;

    @Enumerated(EnumType.ORDINAL)
    private IncidentType incidentType;

    @ManyToOne
    @JoinColumn(name="vulnerability_id")
    private Vulnerability vulnerability;

    @ManyToMany
    @JoinTable(
            name = "agent_incident",
            joinColumns = @JoinColumn(name = "incident_id"),
            inverseJoinColumns = @JoinColumn(name = "agent_id"))
    private Set<Agent> detectedOnAgents;

    private String relevantPointInApplication;

    @Column(length = 2500)
    private String query;

    public Incident() {
        this.detectedOnAgents = new HashSet<>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public StackTraceEntity getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceEntity stackTrace) {
        this.stackTrace = stackTrace;
    }

    public HttpRequestEntity getHttpRequestEntity() {
        return httpRequestEntity;
    }

    public void setHttpRequestEntity(HttpRequestEntity httpRequestEntity) {
        this.httpRequestEntity = httpRequestEntity;
    }

    public IncidentType getIncidentType() {
        return incidentType;
    }

    public void setIncidentType(IncidentType incidentType) {
        this.incidentType = incidentType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Set<Agent> getDetectedOnAgents() {
        return detectedOnAgents;
    }

    public void setDetectedOnAgents(Set<Agent> detectedOnAgents) {
        this.detectedOnAgents = detectedOnAgents;
    }

    public void addToDetectedOnAgents(Agent agent) {
        this.detectedOnAgents.add(agent);
    }

    public String getRelevantPointInApplication() {
        return relevantPointInApplication;
    }

    public void setRelevantPointInApplication(String relevantPointInApplication) {
        this.relevantPointInApplication = relevantPointInApplication;
    }

    public Vulnerability getVulnerability() {
        return vulnerability;
    }

    public void setVulnerability(Vulnerability vulnerability) {
        this.vulnerability = vulnerability;
    }

}
