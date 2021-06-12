package de.vms.javaagent.sensor.csrfchecker.request;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vms.javaagent.reporting.IncidentReporter;
import de.vms.javaagent.reporting.model.Incident;
import de.vms.javaagent.reporting.model.IncidentType;
import de.vms.javaagent.sensor.csrfchecker.config.CsrfCheckerSensorConfig;
import de.vms.javaagent.sensor.http.request.IastHttpRequest;
import de.vms.javaagent.sensor.http.advice.RequestAdvice;

public class CsrfCheckerAdvice implements RequestAdvice {

    private final Logger LOG = LoggerFactory.getLogger(CsrfCheckerAdvice.class);
    private static final Set<String> CSRFCHECK_METHODS = new HashSet<>(Arrays.asList("POST", "PUT", "DELETE", "PATCH"));

    private CsrfCheckerSensorConfig csrfCheckerSensorConfig;

    public CsrfCheckerAdvice(CsrfCheckerSensorConfig csrfCheckerSensorConfig) {
        this.csrfCheckerSensorConfig = csrfCheckerSensorConfig;
    }

    @Override
    public void intercept(IastHttpRequest request) {
        if (!passesCsrfCheck(request)) {
            Incident incident = new Incident(IncidentType.MISSING_CSRF_TOKEN);
            IncidentReporter.report(incident);
        }
    }

    private boolean passesCsrfCheck(IastHttpRequest request) {
        if (!CSRFCHECK_METHODS.contains(request.getMethod())) {
            return true;
        }

        Map<String, String> header = request.getHeader();
        Set<String> csrfHeaderNames = csrfCheckerSensorConfig.getCsrfHeaderNames();

        LOG.debug("keys in request: {}", request.getHeader().keySet());
        for (String headerName : csrfHeaderNames) {
            if (header.containsKey(headerName.toLowerCase()) || header.containsKey(headerName)) {
                return true;
            }
        }

        return false;
    }
}
