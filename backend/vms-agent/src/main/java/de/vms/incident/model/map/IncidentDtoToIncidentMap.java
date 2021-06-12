package de.vms.incident.model.map;

import org.modelmapper.PropertyMap;

import de.vms.incident.model.Incident;
import de.vms.incident.model.converter.HttpRequestDtoToHttpRequestConverter;
import de.vms.incident.model.converter.StackTraceDtoToStackTraceEntityConverter;
import de.vms.incident.model.dto.IncidentDto;

public class IncidentDtoToIncidentMap extends PropertyMap<IncidentDto, Incident> {
    @Override
    protected void configure() {
        using(new StackTraceDtoToStackTraceEntityConverter()).map(source.getStackTrace()).setStackTrace(null);
        using(new HttpRequestDtoToHttpRequestConverter()).map(source.getHttpRequest()).setHttpRequestEntity(null);
    }
}
