package de.vms.gate.model.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GateData {
    private List<GateConditionElement> conditions;

    public GateData() {
    }

    public GateData(List<GateConditionElement> conditions) {
        this.conditions = conditions;
    }

    public List<GateConditionElement> getConditions() {
        return conditions;
    }

    public void setConditions(List<GateConditionElement> conditions) {
        this.conditions = conditions;
    }
}
