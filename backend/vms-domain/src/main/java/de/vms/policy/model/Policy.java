package de.vms.policy.model;

import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private boolean sqlInjectionDetectionEnabled;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "csrfCheckerConfig_id", referencedColumnName = "id")
    private CsrfCheckerConfig csrfCheckerConfig;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "taintTrackerConfig_id", referencedColumnName = "id")
    private TaintTrackerConfig taintTrackerConfig;

    private boolean csrfCheckEnabled;

    public Policy() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public boolean isSqlInjectionDetectionEnabled() {
        return sqlInjectionDetectionEnabled;
    }

    public void setSqlInjectionDetectionEnabled(boolean sqlInjectionDetectionEnabled) {
        this.sqlInjectionDetectionEnabled = sqlInjectionDetectionEnabled;
    }

    public boolean isCsrfCheckEnabled() {
        return csrfCheckEnabled;
    }

    public void setCsrfCheckEnabled(boolean csrfCheckEnabled) {
        this.csrfCheckEnabled = csrfCheckEnabled;
    }

    public CsrfCheckerConfig getCsrfCheckerConfig() {
        return csrfCheckerConfig;
    }

    public void setCsrfCheckerConfig(CsrfCheckerConfig csrfCheckerConfig) {
        this.csrfCheckerConfig = csrfCheckerConfig;
    }

    public TaintTrackerConfig getTaintTrackerConfig() {
        return taintTrackerConfig;
    }

    public void setTaintTrackerConfig(TaintTrackerConfig taintTrackerConfig) {
        this.taintTrackerConfig = taintTrackerConfig;
    }
}