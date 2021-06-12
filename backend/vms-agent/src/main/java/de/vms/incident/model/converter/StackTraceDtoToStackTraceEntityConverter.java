package de.vms.incident.model.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import de.vms.incident.model.StackTraceEntity;
import de.vms.incident.model.dto.StackTraceElementDto;

public class StackTraceDtoToStackTraceEntityConverter implements Converter<List<StackTraceElementDto>, StackTraceEntity> {

    @Override
    public StackTraceEntity convert(MappingContext<List<StackTraceElementDto>, StackTraceEntity> context) {
        List<StackTraceElementDto> stackTraceElementDtos = context.getSource();

        List<String> stackTraceElementStrings = stackTraceElementDtos
                .stream()
                .map(stackTraceElement -> stackTraceElement.toString())
                .collect(
                        Collectors.toList());

        StackTraceEntity output = new StackTraceEntity(stackTraceElementStrings);
        return output;
    }
}
