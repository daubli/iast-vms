package de.vms.incident.model.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import de.vms.incident.model.HttpRequestEntity;
import de.vms.incident.model.dto.HttpRequestDto;

public class HttpRequestDtoToHttpRequestConverter implements Converter<HttpRequestDto, HttpRequestEntity> {
    @Override
    public HttpRequestEntity convert(MappingContext<HttpRequestDto, HttpRequestEntity> context) {
        HttpRequestDto httpRequestDto = context.getSource();

        HttpRequestEntity outputEntity = new HttpRequestEntity();
        outputEntity.setMethod(httpRequestDto.getMethod());
        outputEntity.setRequestURI(httpRequestDto.getRequestURI());
        outputEntity.setRequestURL(httpRequestDto.getRequestURL());
        outputEntity.setQuery(httpRequestDto.getQueryString());
        return outputEntity;
    }
}
