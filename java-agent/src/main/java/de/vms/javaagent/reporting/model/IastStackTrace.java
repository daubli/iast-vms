package de.vms.javaagent.reporting.model;

import java.util.List;

public class IastStackTrace {
    private List<IastStackTraceElement> stackTrace;

    public IastStackTrace(List<IastStackTraceElement> stackTrace) {
        this.stackTrace = stackTrace;
    }

    public List<IastStackTraceElement> getStackTrace() {
        return stackTrace;
    }
}
