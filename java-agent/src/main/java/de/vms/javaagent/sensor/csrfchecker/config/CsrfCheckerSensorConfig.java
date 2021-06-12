package de.vms.javaagent.sensor.csrfchecker.config;

import java.util.Set;

public class CsrfCheckerSensorConfig {
    private Set<String> csrfHeaders;

    public CsrfCheckerSensorConfig(Set<String> csrfHeaderNames) {
        this.csrfHeaders = csrfHeaderNames;
    }

    public Set<String> getCsrfHeaderNames() {
        if (csrfHeaders == null) {
            //default values
            return Set.of("X-XSRF-TOKEN","X-Requested-With","Requested-By");
        }
        return csrfHeaders;
    }
}
