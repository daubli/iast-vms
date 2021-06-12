package de.vms.javaagent.sensor.http.advice;

import de.vms.javaagent.sensor.http.request.IastHttpRequest;

public interface RequestAdvice {

    void intercept(IastHttpRequest request);
}
