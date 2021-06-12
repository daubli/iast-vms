package de.vms.filter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MainFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(MainFilter.class);

    private static final String URL_PREFIX_FOR_LOGGING = "/api";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) {
        final Instant start = Instant.now();

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String requestURI = request.getRequestURI();

        boolean logRequest = requestURI.startsWith(URL_PREFIX_FOR_LOGGING);

        try {
            if (logRequest) {
                LOG.info("{} {} started", request.getMethod(), requestURI);
            }
            chain.doFilter(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (logRequest) {
                LOG.info("{} {} completed with status code {} in {}ms", request.getMethod(), requestURI,
                        response.getStatus(), start.until(Instant.now(), ChronoUnit.MILLIS));
            }
        }
    }
}
