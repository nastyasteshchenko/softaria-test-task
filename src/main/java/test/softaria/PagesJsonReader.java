package test.softaria;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

final class PagesJsonReader {
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private PagesJsonReader() {
    }

    static HashMap<String, String> readToHashMap(String file) throws IOException {
        return OBJECT_MAPPER.readValue(new File(file), new TypeReference<>() {
        });
    }
}
