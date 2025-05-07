package utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class CsvDataLoader {

    public static List<Map<String, String>> loadTestData(String filename) {
        String profile = System.getProperty("test-profile", "SIT"); // default to SIT if not set
        String fullPath = "test-data/" + profile + "/" + filename;

        List<Map<String, String>> data = new ArrayList<>();

        try (InputStream inputStream = CsvDataLoader.class.getClassLoader().getResourceAsStream(fullPath)) {
            if (inputStream == null) {
                throw new RuntimeException("Test data file not found: " + fullPath);
            }

            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            String[] headers = reader.readNext(); // first row as headers
            if (headers == null) {
                throw new RuntimeException("CSV file is empty: " + fullPath);
            }

            String[] values;
            while ((values = reader.readNext()) != null) {
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    String key = headers[i].trim();
                    String value = (i < values.length) ? values[i].trim() : "";
                    row.put(key, value);
                }
                data.add(row);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to load test data from " + fullPath, e);
        }

        return data;
    }
}
