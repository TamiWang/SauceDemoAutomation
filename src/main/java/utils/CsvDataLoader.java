package utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CsvDataLoader {
    public static List<Map<String, String>> loadTestData(String resourcePath) {
        List<Map<String, String>> data = new ArrayList<>();

        try (InputStream inputStream = CsvDataLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                throw new RuntimeException("File not found in classpath: " + resourcePath);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String[] headers = reader.readLine().split(",");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1); // preserve empty trailing fields
                Map<String, String> row = new HashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    String key = headers[i].trim();
                    String value = (i < values.length) ? values[i].trim() : "";
                    row.put(key, value);
                }

                data.add(row);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data from " + resourcePath, e);
        }

        return data;
    }
}

