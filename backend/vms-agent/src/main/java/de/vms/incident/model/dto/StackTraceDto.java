package de.vms.incident.model.dto;

import java.util.List;

public class StackTraceDto {

    List<StackTraceElementDto> stackTrace;

    public StackTraceDto() {

    }

    public List<StackTraceElementDto> getStackTraceElements() {
        return stackTrace;
    }
}
