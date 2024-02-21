package com.demo.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {

    private JsonUtil() {}

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final ObjectWriter OBJECT_WRITER = OBJECT_MAPPER.writerWithDefaultPrettyPrinter();

    static {
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    public static String writeAsString(Object content) {
        try {
            return OBJECT_MAPPER.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            log.error("JSON serialize failed: ", e);
            return "";
        }
    }

    public static String writeAsStringPretty(Object content) {
        try {
            return OBJECT_WRITER.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            log.error("JSON serialize failed: ", e);
            return "";
        }
    }

    public static <T> T readObject(String content, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(content, type);
        } catch (JsonProcessingException e) {
            log.error("JSON deserialize failed: ", e);
            return null;
        }
    }
}
