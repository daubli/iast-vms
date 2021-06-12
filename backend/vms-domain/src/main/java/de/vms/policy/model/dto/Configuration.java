package de.vms.policy.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.vms.policy.model.CsrfCheckerConfig;
import de.vms.policy.model.TaintTrackerConfig;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Configuration {

    private TaintTrackerConfig taintTrackerConfig;

    private CsrfCheckerConfig csrfCheckerConfig;

    @JsonProperty("TAINT_TRACKER")
    public TaintTrackerConfig getTaintTrackerConfig() {
        return taintTrackerConfig;
    }

    public void setTaintTrackerConfig(TaintTrackerConfig taintTrackerConfig) {
        this.taintTrackerConfig = taintTrackerConfig;
    }

    @JsonProperty("CSRF_CHECKER")
    public CsrfCheckerConfig getCsrfCheckerConfig() {
        return csrfCheckerConfig;
    }

    public void setCsrfCheckerConfig(CsrfCheckerConfig csrfCheckerConfig) {
        this.csrfCheckerConfig = csrfCheckerConfig;
    }
}
