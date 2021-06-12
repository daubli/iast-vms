package de.vms.incident.stacktrace;

import java.util.Optional;

import de.vms.incident.model.StackTraceEntity;

public class StackTraceUtil {

    public static Optional<String> getFirstOccuringLineOfPackageInStackTrace(StackTraceEntity ste, String packageMain) {
        if (ste != null) {
            return ste.getStackOfCalls().stream().filter(item -> item.startsWith(packageMain)).findFirst();
        } else {
            return Optional.empty();
        }
    }
}
