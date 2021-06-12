package de.vms.policy.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Method {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(mappedBy = "method")
    private List<MethodArgument> arguments;

    private String bySuperType;
    private String byReturnType;
    private String byClass;
    private String methodName;
    private String byClassAnnotation;
    private String byMethodAnnotation;

    @Enumerated(EnumType.ORDINAL)
    private Taint taint;

    @ManyToOne
    @JoinColumn(name="source_taint_tracker_id")
    private TaintTrackerConfig sourceInTaintTrackerConfig;

    @ManyToOne
    @JoinColumn(name="sink_taint_tracker_id")
    private TaintTrackerConfig sinkInTaintTrackerConfig;

    @ManyToOne
    @JoinColumn(name="taint_trough_taint_tracker_id")
    private TaintTrackerConfig taintThroughInTaintTrackerConfig;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<MethodArgument> getArguments() {
        return arguments;
    }

    public void setArguments(List<MethodArgument> arguments) {
        this.arguments = arguments;
    }

    public String getBySuperType() {
        return bySuperType;
    }

    public void setBySuperType(String bySuperType) {
        this.bySuperType = bySuperType;
    }

    public String getByReturnType() {
        return byReturnType;
    }

    public void setByReturnType(String byReturnType) {
        this.byReturnType = byReturnType;
    }

    public String getByClass() {
        return byClass;
    }

    public void setByClass(String byClass) {
        this.byClass = byClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getByClassAnnotation() {
        return byClassAnnotation;
    }

    public void setByClassAnnotation(String byClassAnnotation) {
        this.byClassAnnotation = byClassAnnotation;
    }

    public String getByMethodAnnotation() {
        return byMethodAnnotation;
    }

    public void setByMethodAnnotation(String byMethodAnnotation) {
        this.byMethodAnnotation = byMethodAnnotation;
    }

    public Taint getTaint() {
        return taint;
    }

    public void setTaint(Taint taint) {
        this.taint = taint;
    }
}
