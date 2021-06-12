package de.vms.gate.model.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.vms.gate.model.GateConditionMetric;

@Converter(autoApply = true)
public class GateConditionMetricConverter implements AttributeConverter<GateConditionMetric, String> {
    @Override
    public String convertToDatabaseColumn(GateConditionMetric metric) {
        if (metric == null) {
            return null;
        }
        return metric.getValue();
    }

    @Override
    public GateConditionMetric convertToEntityAttribute(String metric) {
        if (metric == null) {
            return null;
        }

        return Stream.of(GateConditionMetric.values())
                .filter(c -> c.getValue().equals(metric))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

