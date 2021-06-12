package de.vms.incident.model.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.vms.incident.model.IncidentType;

@Converter(autoApply = true)
public class IncidentTypeConverter implements AttributeConverter<IncidentType, String> {
    @Override
    public String convertToDatabaseColumn(de.vms.incident.model.IncidentType incidentType) {
        if (incidentType == null) {
            return null;
        }
        return incidentType.getType();
    }

    @Override
    public IncidentType convertToEntityAttribute(String incidentType) {
        if (incidentType == null) {
            return null;
        }

        return Stream.of(IncidentType.values())
                .filter(c -> c.getType().equals(incidentType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}