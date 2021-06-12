package de.vms.incident.model.dto;

public class StackTraceElementDto {

    String moduleName;

    String moduleVersion;

    String declaringClass;

    String methodName;

    String fileName;

    int lineNumber;

    public StackTraceElementDto() {
        //for deserialization/serialization
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleVersion() {
        return moduleVersion;
    }

    public String getDeclaringClass() {
        return declaringClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String toString() {
        String s = "";
        if (moduleName != null && !moduleName.isEmpty()) {
            s += moduleName;

            if (moduleVersion != null &&
                    !moduleVersion.isEmpty()) {
                s += "@" + moduleVersion;
            }
        }
        s = s.isEmpty() ? declaringClass : s + "/" + declaringClass;

        return s + "." + methodName + "(" +
                (fileName != null && lineNumber >= 0 ?
                        fileName + ":" + lineNumber + ")" :
                        (fileName != null ? "" + fileName + ")" : "Unknown Source)"));
    }
}
