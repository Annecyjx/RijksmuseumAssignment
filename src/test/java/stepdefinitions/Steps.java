package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class Steps {

    private Response response;
    private final String BASE_URL = "https://data.rijksmuseum.nl/search/collection";

    @Given("the search API from Rijksmuseum is available")
    public void apiIsAvailable() {
        response = given()
            .queryParam("type", "painting")
            .queryParam("material", "oil paint")
            .when()
            .get(BASE_URL);
        assertEquals("API is not available", 200, response.statusCode());
    }

    @When("I search for {string}")
    public void searchTypes(String type) {
        response = given()
            .queryParam("type", type)
            .when()
            .get(BASE_URL);
    }

    @Then("I should receive a successful response with a list of results")
    public void validateSearchResults() {
        assertEquals("API did not return 200 OK", 200, response.getStatusCode());

        List<Map<String, Object>> orderedItems = response.jsonPath().getList("orderedItems");

        assertNotNull("orderedItems list is null", orderedItems);
        assertTrue("No orderedItems found in response", orderedItems.size() > 0);
    }

    @Then("I should receive a successful response with no results")
    public void validateNoResults() {
        assertEquals("API did not return 200 OK", 200, response.getStatusCode());

        List<Map<String, Object>> orderedItems = response.jsonPath().getList("orderedItems");
        assertNotNull("orderedItems list is null", orderedItems);
        assertTrue("Expected no results but found some", orderedItems.isEmpty());
    }

}
