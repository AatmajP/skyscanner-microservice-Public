package com.skyscanner;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a search request with a city filter.
 */
public class Search {

    @JsonProperty
    private String city;

    // Default constructor (required by Jackson for deserialization)
    public Search() {
    }

    // Parameterized constructor
    public Search(String city) {
        this.city = city;
    }

    // Getter
    @JsonProperty
    public String getCity() {
        return city;
    }
}
