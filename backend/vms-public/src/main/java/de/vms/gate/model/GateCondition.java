package de.vms.gate.model;

import java.util.UUID;

import javax.persistence.*;

import de.vms.gate.model.converter.GateConditionMetricConverter;
import de.vms.gate.model.converter.GateConditionOperatorConverter;

@Entity
public class GateCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Convert(converter = GateConditionMetricConverter.class)
    @Enumerated(EnumType.ORDINAL)
    private GateConditionMetric metric;

    @Convert(converter = GateConditionOperatorConverter.class)
    @Enumerated(EnumType.ORDINAL)
    private GateConditionOperator operator;

    private String value;

    public GateCondition() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GateConditionMetric getMetric() {
        return metric;
    }

    public void setMetric(GateConditionMetric metric) {
        this.metric = metric;
    }

    public GateConditionOperator getOperator() {
        return operator;
    }

    public void setOperator(GateConditionOperator operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
