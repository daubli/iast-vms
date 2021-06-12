package de.vms.javaagent.sensor.tainttracker.config;

public class MethodArgumentsConfig {
    private String type;

    public MethodArgumentsConfig(String type) {
        this.type = type;
    }

    public String getType() {
        if (type == null) {
            return "";
        }

        return type;
    }
}
