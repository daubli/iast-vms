package de.vms.gate.model;

public enum GateHealth {
    NA("na"), PASSING("passing"), NOT_PASSING("not passing");

    String value;

    GateHealth() {
    }

    GateHealth(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GateHealth fromString(String text) {
        for (GateHealth b : GateHealth.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
