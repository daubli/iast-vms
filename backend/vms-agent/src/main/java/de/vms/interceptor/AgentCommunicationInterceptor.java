package de.vms.interceptor;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import de.vms.agent.service.AgentService;

@Component
public class AgentCommunicationInterceptor implements HandlerInterceptor {

    private static final String INTERNAL_AGENT_PREFIX = "/api/internal/";
    private AgentService agentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (isAgentRequest(request)) {
            String vmsAgentUUID = request.getHeader("X-VMS-AGENT");
            if (vmsAgentUUID != null) {
                agentService.updateLastSeen(UUID.fromString(vmsAgentUUID));
                return true;
            }
        }
        return false;
    }

    private boolean isAgentRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith(INTERNAL_AGENT_PREFIX);
    }

    @Autowired
    public void setAgentService(AgentService agentService) {
        this.agentService = agentService;
    }
}
