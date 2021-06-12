package de.vms.policy.model.converter;

import java.util.stream.Stream;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import de.vms.policy.model.Taint;

@Converter(autoApply = true)
public class TaintConverter implements AttributeConverter<Taint, String> {

    @Override
    public String convertToDatabaseColumn(Taint taint) {
        if (taint == null) {
            return null;
        }
        return taint.getCode();
    }

    @Override
    public Taint convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Taint.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}