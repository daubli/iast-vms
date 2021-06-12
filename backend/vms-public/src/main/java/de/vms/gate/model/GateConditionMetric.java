package de.vms.gate.model;

public enum GateConditionMetric {

    RATING("rating"),
    NUMBER_OF_HIGH_VUL("nohighvul"),
    NUMBER_OF_MOD_VUL("nomoderatevul"),
    NUMBER_OF_LOW_VUL("nolowvul");

    String value;

    GateConditionMetric(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GateConditionMetric fromString(String text) {
        for (GateConditionMetric b : GateConditionMetric.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
