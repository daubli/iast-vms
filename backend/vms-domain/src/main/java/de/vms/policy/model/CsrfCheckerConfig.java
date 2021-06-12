package de.vms.policy.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.*;

@Entity
public class CsrfCheckerConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ElementCollection
    private Set<String> csrfHeaders;

    public Set<String> getCsrfHeaders() {
        return csrfHeaders;
    }

    public void setCsrfHeaders(Set<String> csrfHeaders) {
        this.csrfHeaders = csrfHeaders;
    }
}
