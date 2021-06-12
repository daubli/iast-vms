package de.vms.javaagent.config;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import de.vms.javaagent.api.base.SimpleHttpClient;

public class StartConfigLoader {

    private AgentConfiguration agentConfiguration;

    private Logger LOG = LoggerFactory.getLogger(StartConfigLoader.class);

    private final String CONFIG_URL = "/api/internal/config/";

    public StartConfigLoader(AgentConfiguration agentConfiguration) {
        this.agentConfiguration = agentConfiguration;
    }

    public List<SensorConfiguration> fetchStartConfiguration() throws Exception {
        ArrayList<SensorConfiguration> configs = new ArrayList<>();

        SimpleHttpClient client = new SimpleHttpClient(agentConfiguration);

        HttpResponse<String> stringHttpResponse = client.get(CONFIG_URL);

        if (stringHttpResponse != null && stringHttpResponse.statusCode() == HttpStatus.SC_OK) {
            String body = stringHttpResponse.body();
            JsonElement jsonElement = new Gson().fromJson(body, JsonObject.class);

            if (jsonElement == null) {
                return configs;
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();

            for (Map.Entry<String, JsonElement> entry : entries) {
                SensorConfiguration modConf = new SensorConfiguration(entry.getKey(), entry.getValue());
                configs.add(modConf);
            }
        }
        return configs;
    }
}
