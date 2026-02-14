package com.skyscanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST resource that handles search requests.
 *
 * Endpoints:
 * POST /search — accepts JSON body {"city":"..."}
 * GET /search?city= — accepts city as a query parameter (browser-friendly)
 */
@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final List<SearchResult> searchResults;

    /**
     * Constructor — receives the pre-loaded list of all search results.
     *
     * @param searchResults combined list of hotels and rental cars
     */
    public SearchResource(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    /**
     * Handles GET /search?city=Edinburgh requests (browser-friendly).
     *
     * If no city is provided, returns all results.
     *
     * @param city the city to filter by (query parameter)
     * @return a list of matching SearchResult objects
     */
    @GET
    public List<SearchResult> searchByQuery(@QueryParam("city") String city) {
        if (city == null || city.isEmpty()) {
            return searchResults;
        }
        return searchResults.stream()
                .filter(result -> result.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    /**
     * Handles POST /search requests.
     *
     * Filters the search results by the city provided in the request body
     * (case-insensitive comparison).
     *
     * @param search the search request containing the city to filter by
     * @return a list of SearchResult objects whose city matches the query
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public List<SearchResult> search(Search search) {
        String queriedCity = search.getCity();

        return searchResults.stream()
                .filter(result -> result.getCity().equalsIgnoreCase(queriedCity))
                .collect(Collectors.toList());
    }
}
