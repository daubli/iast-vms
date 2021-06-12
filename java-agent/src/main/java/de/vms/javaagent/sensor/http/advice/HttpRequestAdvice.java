package de.vms.javaagent.sensor.http.advice;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bytebuddy.asm.Advice;

import de.vms.javaagent.sensor.http.HttpRequestStorage;
import de.vms.javaagent.sensor.http.request.HttpRequestDataFetcher;
import de.vms.javaagent.sensor.http.request.IastHttpRequest;
import de.vms.javaagent.sensor.http.request.IastHttpRequestBuilder;

public class HttpRequestAdvice {

    public static HttpRequestStorage httpRequestStorage;

    public static HttpRequestDataFetcher httpRequestDataFetcher;

    public static final Logger LOG = LoggerFactory.getLogger(HttpRequestAdvice.class);

    public static void initializeStaticFields() {
        httpRequestStorage = HttpRequestStorage.getInstance();
        httpRequestDataFetcher = new HttpRequestDataFetcher();
    }

    @Advice.OnMethodEnter
    public static void intercept(@Advice.AllArguments Object[] allArguments, @Advice.Origin Method method) {
        LOG.debug("{}.{} was called.", method.getDeclaringClass(), method.getName());
        for (Object argument : allArguments) {
            try {
                if ((Class.forName("javax.servlet.http.HttpServletRequest")).isInstance(argument)) {
                    Object request = argument;
                    IastHttpRequest iastHttpRequest = new IastHttpRequestBuilder().withMethod(
                            httpRequestDataFetcher.getHttpGetMethodForRequest(request))
                            .withQuery(httpRequestDataFetcher.getHttpGetQueryForRequest(request))
                            .withRequestURL(httpRequestDataFetcher.getHttpGetRequestURLForRequest(request))
                            .withRequestURI(httpRequestDataFetcher.getHttpGetRequestURIForRequest(request))
                            .withHeader(httpRequestDataFetcher.getHeadersForRequest(request)).build();
                    httpRequestStorage.putRequestToRequestStorage(Thread.currentThread().getId(), iastHttpRequest);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
