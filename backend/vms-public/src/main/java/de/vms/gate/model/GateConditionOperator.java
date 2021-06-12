package de.vms.gate.model;

public enum GateConditionOperator {
    LESS_THAN("lt"), GREATER_THAN("gt"), WORSE_THAN("wt"), BETTER_THAN("bt");

    String value;

    GateConditionOperator() {
    }

    GateConditionOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static GateConditionOperator fromString(String text) {
        for (GateConditionOperator b : GateConditionOperator.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}