package de.vms.config.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import de.vms.policy.model.Method;
import de.vms.policy.model.MethodArgument;
import de.vms.policy.model.Taint;
import de.vms.policy.model.TaintTrackerConfig;
import de.vms.policy.model.CsrfCheckerConfig;
import de.vms.policy.model.Policy;
import de.vms.policy.service.PolicyService;

@Service
public class AgentConfigurationService {

    private PolicyService policyService;

    public AgentConfigurationService(PolicyService policyService) {
        this.policyService = policyService;
    }

    public boolean isCsrfCheckActivated() {
        return policyService.getPolicy().map(p -> p.isCsrfCheckEnabled()).orElse(false);
    }

    public CsrfCheckerConfig getCsrfCheckerConfig() {
        CsrfCheckerConfig csrfCheckerConfig = new CsrfCheckerConfig();
        return policyService.getPolicy().map(Policy::getCsrfCheckerConfig).orElse(csrfCheckerConfig);
    }

    public boolean isTaintTrackerActivated() {
        return policyService.getPolicy().map(Policy::isSqlInjectionDetectionEnabled).orElse(false);
    }

    public TaintTrackerConfig getTaintTrackerConfig() {
        TaintTrackerConfig ttc = new TaintTrackerConfig();

        List<Method> sinks = generateSinks();
        List<Method> sources= generateSources();
        List<Method> taintTrough = generateTaintThroughMethods();

        ttc.setSources(sources);
        ttc.setSinks(sinks);
        ttc.setTaintThrough(taintTrough);
        
        return ttc;
    }

    private List<Method> generateTaintThroughMethods() {
        MethodArgument maString = new MethodArgument();
        maString.setType("java.lang.String");

        MethodArgument maChart = new MethodArgument();
        maChart.setType("java.nio.charset.Charset");

        Method taintTrough = new Method();
        taintTrough.setArguments(List.of(maString, maChart));
        taintTrough.setByReturnType("java.lang.String");
        taintTrough.setMethodName("decode");
        taintTrough.setByClass("java.net.URLDecoder");

        return Collections.singletonList(taintTrough);
    }

    private List<Method> generateSources() {
        List<Method> resultList = new ArrayList<>();
        final String controller = "*Controller";

        Method source = new Method();
        source.setByMethodAnnotation("org.springframework.web.bind.annotation.GetMapping");
        source.setTaint(Taint.PARAMETER);
        source.setByClass(controller);
        resultList.add(source);

        Method source2 = new Method();
        source2.setByMethodAnnotation("org.springframework.web.bind.annotation.PutMapping");
        source2.setTaint(Taint.PARAMETER);
        source2.setByClass(controller);
        resultList.add(source2);

        Method source3 = new Method();
        source3.setByMethodAnnotation("org.springframework.web.bind.annotation.DeleteMapping");
        source3.setTaint(Taint.PARAMETER);
        source3.setByClass(controller);
        resultList.add(source3);

        Method source4 = new Method();
        source4.setByMethodAnnotation("org.springframework.web.bind.annotation.PatchMapping");
        source4.setTaint(Taint.PARAMETER);
        source4.setByClass(controller);
        resultList.add(source4);

        Method source5 = new Method();
        source5.setByMethodAnnotation("org.springframework.web.bind.annotation.PostMapping");
        source5.setTaint(Taint.PARAMETER);
        source5.setByClass(controller);
        resultList.add(source5);

        Method source6 = new Method();
        source6.setBySuperType("javax.servlet.http.HttpServletRequest");
        source6.setMethodName("getQueryString");
        source6.setTaint(Taint.RETURN_VALUE);
        resultList.add(source6);

        Method source7 = new Method();
        source7.setBySuperType("javax.servlet.http.HttpServletRequest");
        source7.setMethodName("getRequestURI");
        source7.setTaint(Taint.RETURN_VALUE);
        resultList.add(source7);

        Method source8 = new Method();
        source8.setBySuperType("javax.servlet.http.HttpServletRequest");
        source8.setMethodName("getParameterMap");
        source8.setTaint(Taint.RETURN_VALUE);
        resultList.add(source8);

        MethodArgument stringMethodArgument = new MethodArgument();
        stringMethodArgument.setType("java.lang.String");

        Method source9 = new Method();
        source9.setBySuperType("javax.servlet.http.HttpServletRequest");
        source9.setMethodName("getParameter");
        source9.setArguments(Collections.singletonList(stringMethodArgument));
        source9.setTaint(Taint.RETURN_VALUE);
        resultList.add(source9);

        Method source10 = new Method();
        source10.setBySuperType("javax.servlet.http.HttpServletRequest");
        source10.setMethodName("getHeaders");
        source10.setArguments(Collections.singletonList(stringMethodArgument));
        source10.setTaint(Taint.RETURN_VALUE);
        resultList.add(source10);

        Method source10a = new Method();
        source10a.setBySuperType("javax.servlet.http.HttpServletRequest");
        source10a.setMethodName("getHeader");
        source10a.setArguments(Collections.singletonList(stringMethodArgument));
        source10a.setTaint(Taint.RETURN_VALUE);
        resultList.add(source10a);

        Method source10b = new Method();
        source10b.setBySuperType("javax.servlet.http.HttpServletRequest");
        source10b.setMethodName("getHeaderNames");
        source10b.setTaint(Taint.RETURN_VALUE);
        resultList.add(source10b);

        Method source11 = new Method();
        source11.setBySuperType("javax.servlet.http.HttpServletRequest");
        source11.setMethodName("getParameterValues");
        source11.setArguments(Collections.singletonList(stringMethodArgument));
        source11.setTaint(Taint.RETURN_VALUE);
        resultList.add(source11);

        Method source12 = new Method();
        source12.setBySuperType("javax.servlet.http.HttpServletRequest");
        source12.setMethodName("getParameterNames");
        source12.setTaint(Taint.RETURN_VALUE);
        resultList.add(source12);

        Method source13 = new Method();
        source13.setBySuperType("javax.servlet.http.HttpServletRequest");
        source13.setMethodName("getCookies");
        source13.setTaint(Taint.RETURN_VALUE);
        resultList.add(source13);

        return resultList;
    }

    private List<Method> generateSinks() {
        List<Method> resultList = new ArrayList<>();

        MethodArgument ma = new MethodArgument();
        ma.setType("java.lang.String");

        Method sink = new Method();
        sink.setBySuperType("java.sql.Connection");
        sink.setMethodName("prepareStatement");
        resultList.add(sink);

        Method sink2 = new Method();
        sink2.setArguments(Collections.singletonList(ma));
        sink2.setBySuperType("java.sql.Connection");
        sink2.setMethodName("nativeSQL");
        resultList.add(sink2);

        Method sink3 = new Method();
        sink3.setArguments(Collections.singletonList(ma));
        sink3.setBySuperType("java.sql.Statement");
        sink3.setMethodName("executeUpdate");
        resultList.add(sink3);

        Method sink4 = new Method();
        sink4.setArguments(Collections.singletonList(ma));
        sink4.setBySuperType("java.sql.Statement");
        sink4.setMethodName("prepareCall");
        resultList.add(sink4);

        Method sink5 = new Method();
        sink4.setArguments(Collections.singletonList(ma));
        sink4.setBySuperType("java.sql.Statement");
        sink4.setMethodName("executeQuery");
        resultList.add(sink4);

        return resultList;
    }
}
