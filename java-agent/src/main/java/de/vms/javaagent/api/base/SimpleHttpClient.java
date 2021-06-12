package de.vms.javaagent.api.base;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.vms.javaagent.config.AgentConfiguration;

public class SimpleHttpClient {

    AgentConfiguration agentConfiguration;

    public static Logger LOG = LoggerFactory.getLogger(SimpleHttpClient.class);

    public SimpleHttpClient(AgentConfiguration agentConfiguration) {
        this.agentConfiguration = agentConfiguration;
    }

    /**
     * Sends a synchronous GET request to the server defined by the instance
     *
     * @param uri
     * @return Http response with string typed body
     * @throws Exception
     */
    public HttpResponse<String> get(String uri) throws Exception {
        uri = extendUriWithBaseUrl(uri);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .header("X-VMS-AGENT", agentConfiguration.getAgentKey())
                .uri(URI.create(uri))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    /**
     * Sends a synchronous POST request to the server defined by the instance
     *
     * @param uri
     * @param data
     * @throws Exception
     */
    public HttpResponse<?> post(String uri, String data) throws Exception {
        uri = extendUriWithBaseUrl(uri);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .header("X-VMS-AGENT", agentConfiguration.getAgentKey())
                .header("Content-Type", "application/json")
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.discarding());
    }

    /**
     * Sends a asynchronous POST request to the server defined by the instance
     *
     * @param uri
     * @param data
     * @throws Exception
     */
    public void postAsync(String uri, String data) throws Exception {
        uri = extendUriWithBaseUrl(uri);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .header("X-VMS-AGENT", agentConfiguration.getAgentKey())
                .header("Content-Type", "application/json")
                .uri(URI.create(uri))
                .POST(HttpRequest.BodyPublishers.ofString(data))
                .build();

        CompletableFuture<HttpResponse<Void>> httpResponseCompletableFuture =
                client.sendAsync(request, HttpResponse.BodyHandlers.discarding());

        httpResponseCompletableFuture.whenComplete(
                (voidHttpResponse, throwable) -> LOG.debug("Agent got status code", voidHttpResponse.statusCode()));
    }

    /**
     * Sends a synchronous PUT request to the server defined by the instance
     *
     * @param uri
     * @param data
     * @return Http response object
     * @throws Exception
     */
    public HttpResponse<?> put(String uri, String data) throws Exception {
        uri = extendUriWithBaseUrl(uri);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .header("X-VMS-AGENT", agentConfiguration.getAgentKey())
                .uri(URI.create(uri))
                .PUT(HttpRequest.BodyPublishers.ofString(data))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.discarding());
    }

    private String extendUriWithBaseUrl(String uri) {
        String baseUrl = agentConfiguration.getVmsURL();
        if (baseUrl != null) {
            uri = new StringBuilder(baseUrl).append(uri).toString();
        }
        return uri;
    }
}
