package de.vms.policy.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "taint_tracker_config")
public class TaintTrackerConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy="sourceInTaintTrackerConfig")
    private List<Method> sources = new ArrayList<>();

    @OneToMany(mappedBy="sinkInTaintTrackerConfig")
    private List<Method> sinks = new ArrayList<>();
    
    @OneToMany(mappedBy="taintThroughInTaintTrackerConfig")
    private List<Method> taintThrough = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Method> getSources() {
        return sources;
    }

    public void setSources(List<Method> sources) {
        this.sources = sources;
    }

    public List<Method> getSinks() {
        return sinks;
    }

    public void setSinks(List<Method> sinks) {
        this.sinks = sinks;
    }

    public List<Method> getTaintThrough() {
        return taintThrough;
    }

    public void setTaintThrough(List<Method> taintThrough) {
        this.taintThrough = taintThrough;
    }
}
