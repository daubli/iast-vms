package de.vms.gate.model.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.vms.gate.model.GateConditionOperator;

@Converter(autoApply = true)
public class GateConditionOperatorConverter implements AttributeConverter<GateConditionOperator, String> {
    @Override
    public String convertToDatabaseColumn(GateConditionOperator operator) {
        if (operator == null) {
            return null;
        }
        return operator.getValue();
    }

    @Override
    public GateConditionOperator convertToEntityAttribute(String operator) {
        if (operator == null) {
            return null;
        }

        return Stream.of(GateConditionOperator.values())
                .filter(c -> c.getValue().equals(operator))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

