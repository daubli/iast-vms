package de.vms.javaagent.config;

import com.google.gson.JsonElement;

public class SensorConfiguration {
    private String module;
    private JsonElement config;

    public String getModule() {
        return module;
    }

    public JsonElement getConfig() {
        return config;
    }

    public SensorConfiguration(String module, JsonElement config) {
        this.module = module;
        this.config = config;
    }
}
