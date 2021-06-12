package de.vms.gate.model.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import de.vms.gate.model.GateCondition;
import de.vms.gate.model.dto.GateConditionElement;

public class GateConditionToGateConditionElementConverter implements Converter<GateCondition, GateConditionElement> {

    @Override
    public GateConditionElement convert(MappingContext<GateCondition, GateConditionElement> context) {
        GateCondition source = context.getSource();

        GateConditionElement output = new GateConditionElement();
        output.setId(source.getId());
        output.setMetric(source.getMetric().getValue());
        output.setOperator(source.getOperator().getValue());
        output.setValue(source.getValue());
        return output;
    }
}
