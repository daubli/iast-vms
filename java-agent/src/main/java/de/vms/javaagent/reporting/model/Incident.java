package de.vms.javaagent.reporting.model;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import de.vms.javaagent.app.Application;
import de.vms.javaagent.sensor.http.request.IastHttpRequest;

public class Incident {

    private LocalDateTime timestamp;

    private List<IastStackTraceElement> stackTrace;

    @SerializedName("applicationMainPackage")
    private String applicationMainPackageOrNull;

    @SerializedName("httpRequest")
    private IastHttpRequest httpRequestOrNull;

    private IncidentType incidentType;

    public Incident(IncidentType incidentType) {
        this.incidentType = incidentType;
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
        this.applicationMainPackageOrNull = Application.getInstance().getMainPackageName();
    }

    public Incident(IncidentType incidentType, List<IastStackTraceElement> stackTrace,
            IastHttpRequest httpRequestOrNull) {
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
        this.stackTrace = stackTrace;
        this.httpRequestOrNull = httpRequestOrNull;
        this.applicationMainPackageOrNull = Application.getInstance().getMainPackageName();
        this.incidentType = incidentType;
    }

    public final LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setStackTrace(List<IastStackTraceElement> stackTrace) {
        this.stackTrace = stackTrace;
    }

    public final List<IastStackTraceElement> getStackTrace() {
        return stackTrace;
    }

    public String getApplicationMainPackageOrNull() {
        return applicationMainPackageOrNull;
    }

    public void setHttpRequestOrNull(IastHttpRequest httpRequestOrNull) {
        this.httpRequestOrNull = httpRequestOrNull;
    }

    public IastHttpRequest getHttpRequestOrNull() {
        return httpRequestOrNull;
    }

    public IncidentType getIncidentType() {
        return incidentType;

    }
}
