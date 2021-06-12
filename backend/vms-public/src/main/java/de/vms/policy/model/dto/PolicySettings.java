package de.vms.policy.model.dto;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicySettings {

    private UUID id;

    private boolean sqlInjectionDetectionEnabled;

    private boolean csrfCheckEnabled;

    private Set<String> possibleCsrfHeaders = Collections.emptySet();

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

    public Set<String> getPossibleCsrfHeaders() {
        return possibleCsrfHeaders;
    }

    public void setPossibleCsrfHeaders(Set<String> possibleCsrfHeaders) {
        this.possibleCsrfHeaders = possibleCsrfHeaders;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
