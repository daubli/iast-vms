package de.vms.javaagent.sensor.tainttracker.config;

import com.google.gson.annotations.SerializedName;

public enum Taint {
    @SerializedName("PARAMETER")
    PARAMETER,
    @SerializedName("RETURN_VALUE")
    RETURN_VALUE
}
