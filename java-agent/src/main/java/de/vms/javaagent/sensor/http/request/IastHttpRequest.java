package de.vms.javaagent.sensor.http.request;

import java.util.Map;

public class IastHttpRequest {

    protected String method;
    protected String requestURL;
    protected String requestURI;
    protected String query;
    protected Map<String, String> header;
    protected String body;

    public IastHttpRequest(String method, String requestURL, String requestURI, String query, Map<String, String> header, String body) {
        this.method = method;
        this.requestURL = requestURL;
        this.requestURI = requestURI;
        this.query = query;
        this.header = header;
        this.body = body;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Map<String, String> getHeader() {
        return header;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public String toString() {
        return "Method: " + method + " URI: " + requestURL + " Query String: " + query + " Header: " + header.toString();
    }
}
