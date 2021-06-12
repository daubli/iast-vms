package de.vms.incident.model;

public enum IncidentType {
    SQL_INJECTION("sql-injection"), MISSING_CSRF_TOKEN("missing-csrf-token");

    String type;

    IncidentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
