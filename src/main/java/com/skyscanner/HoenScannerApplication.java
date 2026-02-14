package com.skyscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Dropwizard application class for HoenScanner.
 *
 * Loads hotel and rental car data from JSON files on startup
 * and registers the SearchResource endpoint.
 */
public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public String getName() {
        return "HoenScanner";
    }

    @Override
    public void initialize(Bootstrap<HoenScannerConfiguration> bootstrap) {
        // Additional bundles or configurations can be added here.
    }

    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws Exception {
        // Create an ObjectMapper for JSON deserialization
        ObjectMapper objectMapper = environment.getObjectMapper();

        // Load rental_cars.json from the resources folder
        List<SearchResult> rentalCars = loadJson(objectMapper, "rental_cars.json");

        // Load hotels.json from the resources folder
        List<SearchResult> hotels = loadJson(objectMapper, "hotels.json");

        // Combine both lists into a single searchResults list
        List<SearchResult> searchResults = new ArrayList<>();
        searchResults.addAll(rentalCars);
        searchResults.addAll(hotels);

        System.out.println("Loaded " + rentalCars.size() + " rental cars and " + hotels.size() + " hotels.");
        System.out.println("Total search results: " + searchResults.size());

        // Register the SearchResource with the combined search results
        environment.jersey().register(new SearchResource(searchResults));
    }

    /**
     * Loads a JSON file from the classpath and deserializes it into a List of SearchResult.
     *
     * @param objectMapper the Jackson ObjectMapper
     * @param fileName     the name of the JSON file in the resources folder
     * @return a list of SearchResult objects
     * @throws IOException if the file cannot be found or read
     */
    private List<SearchResult> loadJson(ObjectMapper objectMapper, String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IOException("Resource not found: " + fileName);
        }
        return objectMapper.readValue(inputStream, new TypeReference<List<SearchResult>>() {});
    }
}
