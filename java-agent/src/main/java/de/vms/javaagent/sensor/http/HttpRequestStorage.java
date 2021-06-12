package de.vms.javaagent.sensor.http;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import de.vms.javaagent.sensor.http.advice.RequestAdvice;
import de.vms.javaagent.sensor.http.request.IastHttpRequest;

public class HttpRequestStorage {

    private static final Map<Long, IastHttpRequest> requestMap = new HashMap<>();

    private static HttpRequestStorage httpRequestStorage;

    private Set<RequestAdvice> requestAdvices = new HashSet<>();

    private HttpRequestStorage() {
    }

    public static synchronized HttpRequestStorage getInstance () {
        if (HttpRequestStorage.httpRequestStorage == null) {
            HttpRequestStorage.httpRequestStorage = new HttpRequestStorage ();
        }
        return HttpRequestStorage.httpRequestStorage;
    }
    

    public void putRequestToRequestStorage(Long threadId, IastHttpRequest request) {
        requestMap.put(threadId, request);
        notifyRequestAdvices(request);
    }

    private void notifyRequestAdvices(IastHttpRequest request) {
        for (RequestAdvice advice : requestAdvices) {
            advice.intercept(request);
        }
    }

    public IastHttpRequest getRequestByThreadId(Long threadId) {
        return requestMap.get(threadId);
    }

    public void registerRequestAdvice(RequestAdvice requestAdvice) {
        requestAdvices.add(requestAdvice);
    }
}
