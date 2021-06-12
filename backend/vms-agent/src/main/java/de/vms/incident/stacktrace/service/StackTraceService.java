package de.vms.incident.stacktrace.service;

import java.math.BigInteger;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;

@Service
public class StackTraceService {

    private EntityManager em;

    public StackTraceService(EntityManager em) {
        this.em = em;
    }
    public Map<String, Long> getStackTraceFrameFrequency() {
        Stream<Object[]> resultStream = em.createNativeQuery("SELECT stesoc.stack_of_calls as call, count(i.id) as idf " +
                "FROM stack_trace_entity_stack_of_calls stesoc "
                + "JOIN incident i on stesoc.stack_trace_entity_id = i.stacktrace_id "
                + "WHERE i.incident_type = 0 GROUP BY stesoc.stack_of_calls ").getResultStream();

        return resultStream.collect(
                Collectors.toMap(tuple -> (String) tuple[0], tuple -> ((BigInteger) tuple[1]).longValue()));
    }

}
