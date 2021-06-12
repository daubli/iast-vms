package de.vms.javaagent.reporting.model;

public class IastStackTraceElement {

    String moduleName;

    String moduleVersion;

    String declaringClass;

    String methodName;

    String fileName;

    int lineNumber;

    public IastStackTraceElement(String moduleName, String moduleVersion, String declaringClass,
            String methodName, String fileName, int lineNumber) {
        this.moduleName = moduleName;
        this.moduleVersion = moduleVersion;
        this.declaringClass = declaringClass;
        this.methodName = methodName;
        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }
}
