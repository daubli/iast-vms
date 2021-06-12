package de.vms.javaagent.sensor.http.request;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestDataFetcher {

    private Class<?> parentClass;
    private Method getMethod;
    private Method getQueryString;
    private Method getRequestURL;
    private Method getRequestURI;
    private Method getHeaderNames;
    private Method getHeader;

    private boolean isInitialized = false;

    public HttpRequestDataFetcher() {
    }

    private void initializeJavaMethods() {
        try {
            parentClass =
                    Thread.currentThread().getContextClassLoader().loadClass("javax.servlet.http.HttpServletRequest");
            getMethod = parentClass.getMethod("getMethod");
            getMethod.setAccessible(true);
            getQueryString = parentClass.getMethod("getQueryString");
            getQueryString.setAccessible(true);
            getRequestURL = parentClass.getMethod("getRequestURL");
            getRequestURL.setAccessible(true);
            getRequestURI = parentClass.getMethod("getRequestURI");
            getRequestURL.setAccessible(true);
            getHeaderNames = parentClass.getMethod("getHeaderNames");
            getHeaderNames.setAccessible(true);
            getHeader = parentClass.getMethod("getHeader", String.class);
            getHeader.setAccessible(true);
            isInitialized = true;
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public String getHttpGetMethodForRequest(Object request) {
        if (!isInitialized) {
            initializeJavaMethods();
        }
        return invokeMethod(getMethod, request);
    }

    public String getHttpGetQueryForRequest(Object request) {
        if (!isInitialized) {
            initializeJavaMethods();
        }
        return invokeMethod(getQueryString, request);
    }

    public String getHttpGetRequestURLForRequest(Object request) {
        if (!isInitialized) {
            initializeJavaMethods();
        }
        return invokeMethod(getRequestURL, request);
    }

    public String getHttpGetRequestURIForRequest(Object request) {
        if (!isInitialized) {
            initializeJavaMethods();
        }
        return invokeMethod(getRequestURI, request);
    }

    public Map<String, String> getHeadersForRequest(Object request) {
        if (!isInitialized) {
            initializeJavaMethods();
        }

        Map<String, String> headerMap = new HashMap<>();

        Enumeration headerNames = invokeMethod(getHeaderNames, request, Enumeration.class);
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = invokeMethod(getHeader, request, String.class, key);
            headerMap.put(key.toLowerCase(), value);
        }
        return headerMap;
    }

    private String invokeMethod(Method method, Object request) {
        try {
            Object resultOrNull = method.invoke(request);
            if (resultOrNull != null) {
                return resultOrNull.toString();
            } else {
                return "";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    private <T> T invokeMethod(Method method, Object request, Class<T> expectedReturnType) {
        try {
            Object resultOrNull = method.invoke(request);
            if (resultOrNull != null) {
                return (T) resultOrNull;
            } 
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> T invokeMethod(Method method, Object request, Class<T> expectedReturnType, Object... parameter) {
        try {
            Object resultOrNull = method.invoke(request, parameter);
            if (resultOrNull != null) {
                return (T) resultOrNull;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
