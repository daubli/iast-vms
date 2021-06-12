package de.vms.javaagent.sensor.http.request;

import java.util.Map;

public class IastHttpRequestBuilder {

    protected String method;
    protected String requestURL;
    protected String requestURI;
    protected String query;
    protected String body;
    protected Map<String, String> header;

    public IastHttpRequestBuilder() {
    }

    public IastHttpRequestBuilder(String method, String requestURL, String requestURI, String query,
            String bodyString) {
        this.method = method;
        this.requestURL = requestURL;
        this.requestURI = requestURI;
        this.query = query;
        this.body = bodyString;
    }

    public IastHttpRequestBuilder withMethod(String method) {
        this.method = method;
        return this;
    }

    public IastHttpRequestBuilder withRequestURL(String requestURL) {
        this.requestURL = requestURL;
        return this;
    }

    public IastHttpRequestBuilder withRequestURI(String requestURI) {
        this.requestURI = requestURI;
        return this;
    }

    public IastHttpRequestBuilder withQuery(String query) {
        this.query = query;
        return this;
    }

    public IastHttpRequestBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public IastHttpRequestBuilder withHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    public IastHttpRequest build() {
        return new IastHttpRequest(method, requestURL, requestURI, query, header, body);
    }
}
