package test.softaria;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class PagesJsonReader {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    HashMap<String, String> readToHashMap(String file) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(file, new TypeReference<>() {
        });
    }
}
