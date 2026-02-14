package com.skyscanner;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a search result item (hotel or rental car).
 */
public class SearchResult {

    @JsonProperty
    private String city;

    @JsonProperty
    private String title;

    @JsonProperty
    private String kind;

    // Default constructor (required by Jackson for deserialization)
    public SearchResult() {
    }

    // Parameterized constructor
    public SearchResult(String city, String title, String kind) {
        this.city = city;
        this.title = title;
        this.kind = kind;
    }

    // Getters
    @JsonProperty
    public String getCity() {
        return city;
    }

    @JsonProperty
    public String getTitle() {
        return title;
    }

    @JsonProperty
    public String getKind() {
        return kind;
    }
}
