package de.vms.javaagent.api.requesthandling;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;

public class JsonSerializerFactory {
    public static Gson create() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        builder.disableHtmlEscaping();
        return builder.create();
    }

    private static class LocalDateTimeFormatter implements JsonSerializer<LocalDateTime> {

        private final DateTimeFormatter formatter;

        public LocalDateTimeFormatter(DateTimeFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(formatter));
        }
    }
}
