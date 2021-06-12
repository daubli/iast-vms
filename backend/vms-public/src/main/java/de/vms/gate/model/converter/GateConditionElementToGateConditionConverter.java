package de.vms.gate.model.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import de.vms.gate.model.GateCondition;
import de.vms.gate.model.GateConditionMetric;
import de.vms.gate.model.GateConditionOperator;
import de.vms.gate.model.dto.GateConditionElement;

public class GateConditionElementToGateConditionConverter implements Converter<GateConditionElement, GateCondition> {

    @Override
    public GateCondition convert(MappingContext<GateConditionElement, GateCondition> context) {
        GateConditionElement source = context.getSource();

        GateCondition output = new GateCondition();
        if (source.getId() != null) {
            output.setId(source.getId());
        }
        output.setMetric(GateConditionMetric.fromString(source.getMetric()));
        output.setOperator(GateConditionOperator.fromString(source.getOperator()));
        output.setValue(source.getValue());
        return output;
    }
}
