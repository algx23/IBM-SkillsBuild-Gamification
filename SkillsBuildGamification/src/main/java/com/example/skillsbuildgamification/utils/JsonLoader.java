package com.example.skillsbuildgamification.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Component
public class JsonLoader {
    private static final String JSON_FILE_PATH = "/avatar_variants.json"; //Load JSON from resources

    public Map<String, Map<String, Map<String, Object>>> loadAvatarData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getResourceAsStream(JSON_FILE_PATH)) {
            if (inputStream == null) {
                throw new IOException("JSON file not found: " + JSON_FILE_PATH);
            }
            return objectMapper.readValue(inputStream, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>(); // Return empty map if file fails to load
        }
    }
}
