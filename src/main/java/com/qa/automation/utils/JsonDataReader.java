package com.qa.automation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class JsonDataReader {
    private static final Logger logger = LoggerFactory.getLogger(JsonDataReader.class);
    private static final String TEST_DATA_PATH = "src/test/resources/testdata/testdata.json";
    private static JsonNode rootNode;

    static {
        loadTestData();
    }

    private static void loadTestData() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            rootNode = mapper.readTree(new File(TEST_DATA_PATH));
            logger.info("Test data loaded successfully from {}", TEST_DATA_PATH);
        } catch (IOException e) {
            logger.error("Failed to load test data file: {}", e.getMessage());
            throw new RuntimeException("Test data file not found at " + TEST_DATA_PATH, e);
        }
    }

    public static JsonNode getTestData(String key) {
        JsonNode node = rootNode.get(key);
        if (node == null) {
            logger.warn("Test data key '{}' not found", key);
        }
        return node;
    }
}
