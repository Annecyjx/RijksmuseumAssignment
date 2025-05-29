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
            .header("Accept", "application/json")
            .when()
            .get(BASE_URL);
        assertEquals("API is not available", 200, response.statusCode());
    }

    @When("I search for {string}")
    public void searchTypes(String type) {
        response = given()
            .header("Accept", "application/json")
            .queryParam("q", type)
            .when()
            .get(BASE_URL);
    }

    @Then("I should receive a successful response with a list of results")
    public void validateSearchResults() {
        assertEquals("API did not return 200 OK", 200, response.getStatusCode());
        assertTrue("No artObjects found in response", response.getBody().asString().contains("artObjects"));
    }

    @Then("I should receive a successful response with no results")
    public void validateNoResults() {
        assertEquals("API did not return 200 OK", 200, response.getStatusCode());
        assertTrue("Expected no results but found some", response.asString().contains("\"artObjects\":[]"));
    }

    @Then("the image URL should be accessible")
    public void validateImageUrl() {
        List<Map<String, Object>> artObjects = response.jsonPath().getList("artObjects");

        assertNotNull("artObjects list is null", artObjects);
        assertTrue("artObjects list is empty", artObjects.size() > 0);

        String imageUrl = response.jsonPath().getString("artObjects[0].webImage.url");
        assertNotNull("Image URL is null or missing", imageUrl);

        Response imageResponse = given()
            .when()
            .get(imageUrl);

        assertEquals(200, imageResponse.getStatusCode());
        assertTrue(imageResponse.getHeader("Content-Type").startsWith("image/"));
    }

}
