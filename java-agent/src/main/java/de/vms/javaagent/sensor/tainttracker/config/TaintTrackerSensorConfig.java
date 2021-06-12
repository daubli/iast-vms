package de.vms.javaagent.sensor.tainttracker.config;

public class TaintTrackerSensorConfig {
    private MethodConfig[] sources;
    private MethodConfig[] sinks;
    private MethodConfig[] taintThrough;

    public TaintTrackerSensorConfig(MethodConfig[] sources, MethodConfig[] taintThrough, MethodConfig[] sinks) {
        this.sources = sources;
        this.sinks = sinks;
        this.taintThrough = taintThrough;
    }

    public MethodConfig[] getSources() {
        if (sources == null) {
            return new MethodConfig[] { new MethodConfig() };
        }
        return sources;
    }

    public MethodConfig[] getSinks() {
        if (sinks == null) {
            return new MethodConfig[] { new MethodConfig() };
        }
        return sinks;
    }

    public MethodConfig[] getTaintThrough() {
        if (taintThrough == null) {
            return new MethodConfig[] { new MethodConfig() };
        }
        return taintThrough;
    }
}
