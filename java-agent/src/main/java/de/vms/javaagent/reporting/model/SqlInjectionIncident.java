package de.vms.javaagent.reporting.model;

public class SqlInjectionIncident extends Incident {
    String query;

    public SqlInjectionIncident(String query) {
        super(IncidentType.SQL_INJECTION);
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
