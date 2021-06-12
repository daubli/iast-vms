package de.vms.javaagent.reporting;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.vms.javaagent.config.AgentConfiguration;
import de.vms.javaagent.reporting.model.IastStackTraceElement;
import de.vms.javaagent.reporting.model.Incident;
import de.vms.javaagent.sensor.http.HttpRequestStorage;
import de.vms.javaagent.sensor.http.request.IastHttpRequest;

public class IncidentReporter {

    public static final HttpRequestStorage httpRequestStorage = HttpRequestStorage.getInstance();

    public static void initializeReporter(AgentConfiguration agentConfiguration) {
        IncidentSubmitter.getInstance().initializeSubmitter(agentConfiguration);
    }

    public static void report(Incident incident) {
        IastHttpRequest requestByThreadId = httpRequestStorage.getRequestByThreadId(Thread.currentThread().getId());

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement[] cleanedUpStackTrace = Arrays.copyOfRange(stackTrace, 3, stackTrace.length - 1);

        List<IastStackTraceElement> shrinkedStackTrace = Arrays.stream(cleanedUpStackTrace).map(stackTraceElement -> {
            return new IastStackTraceElement(stackTraceElement.getModuleName(), stackTraceElement.getModuleVersion(),
                    stackTraceElement.getClassName(), stackTraceElement.getMethodName(),
                    stackTraceElement.getFileName(), stackTraceElement.getLineNumber());
        }).collect(Collectors.toList());

        incident.setHttpRequestOrNull(requestByThreadId);
        incident.setStackTrace(shrinkedStackTrace);

        IncidentSubmitter.getInstance().submit(incident);
    }
}
