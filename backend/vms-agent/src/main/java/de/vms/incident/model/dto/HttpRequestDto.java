package de.vms.incident.model.dto;

public class HttpRequestDto {
    protected String method;

    protected String requestURL;

    protected String requestURI;

    protected String query;

    protected String body;

    public HttpRequestDto() {
        //for deserialization/serialization
    }

    public HttpRequestDto(String method, String requestURL, String requestURI, String query, String body) {
        this.method = method;
        this.requestURL = requestURL;
        this.requestURI = requestURI;
        this.query = query;
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

    public String getQueryString() {
        return query;
    }

    public void setQueryString(String queryString) {
        this.query = queryString;
    }

    public String toString() {
        return "Method: " + method + " URI: " + requestURL + " Query String: " + query;
    }
}
