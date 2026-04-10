package org.example.marketplace.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * @author Shlokov Andrey
 */
public class JsonHelper {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static JSONObject getJsonObject(String path) throws JsonProcessingException {
        String fileToString = "";
        try {
            fileToString = loadFileToString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Map valueMap = mapper.readValue(fileToString, Map.class);
        return new JSONObject(valueMap);
    }

    public static String getJson(String path) {
        try {
            return loadFileToString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadFileToString(String filePath) throws IOException {
        File file = new ClassPathResource(filePath).getFile();
        Path path = Paths.get(file.getAbsolutePath());
        String content = Files.readString(path, StandardCharsets.UTF_8);
        return content.replaceAll("\\r\\n|\\r|\\n|\\t", StringUtils.EMPTY);
    }
}
