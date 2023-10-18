package com.example.figmatelegrambot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    private final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T fromJson(String json, Class<T> aClass) throws JsonProcessingException {
        return mapper.readValue(json, aClass);
    }

    public static String toJson(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }
}
