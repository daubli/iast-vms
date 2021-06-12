package de.vms.javaagent.sensor;

import com.google.gson.Gson;

import de.vms.javaagent.config.SensorConfiguration;
import de.vms.javaagent.sensor.csrfchecker.CsrfCheckerSensor;
import de.vms.javaagent.sensor.tainttracker.TaintTrackerSensor;
import de.vms.javaagent.sensor.csrfchecker.config.CsrfCheckerSensorConfig;
import de.vms.javaagent.sensor.tainttracker.config.TaintTrackerSensorConfig;

public class IASTSensorFactory {
    private static final String TAINT_TRACKER = "TAINT_TRACKER";
    private static final String CSRF_CHECKER = "CSRF_CHECKER";

    private IASTSensorFactory() {
        //not called
    }

    public static Sensor createSensor(final SensorConfiguration config) {
        Gson gson = new Gson();
        if (config.getModule().equals(TAINT_TRACKER)) {
            return new TaintTrackerSensor(gson.fromJson(config.getConfig(), TaintTrackerSensorConfig.class));
        } else if (config.getModule().equals(CSRF_CHECKER)) {
            return new CsrfCheckerSensor(gson.fromJson(config.getConfig(), CsrfCheckerSensorConfig.class));
        }
        return null;
    }
}
