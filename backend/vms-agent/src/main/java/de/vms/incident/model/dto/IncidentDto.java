package de.vms.incident.model.dto;

import java.time.LocalDateTime;
import java.util.List;

public class IncidentDto {
    private LocalDateTime timestamp;
    private List<StackTraceElementDto> stackTrace;
    private HttpRequestDto httpRequest;
    private String applicationMainPackage;
    private String query;
    private String incidentType;

    public IncidentDto() {
        //for deserialization/serialization
    }

    public List<StackTraceElementDto> getStackTrace() {
        return stackTrace;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public HttpRequestDto getHttpRequest() {
        return httpRequest;
    }

    public String getApplicationMainPackage() {
        return applicationMainPackage;
    }

    public String getQuery() {
        return query;
    }

    public String getIncidentType() {
        return incidentType;
    }
}