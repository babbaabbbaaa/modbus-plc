package com.demo.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private JsonUtil() {}

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String writeAsString(Object content) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(content);
    }
}
