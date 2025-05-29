package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class Steps {

    private Response response;
    private final String BASE_URL = "https://www.rijksmuseum.nl/api/en/collection";

    @Given("the search API from Rijksmuseum is available")
    public void apiIsAvailable() {
        response = given()
            .when()
            .get(BASE_URL);
        assertEquals("API is not available", 200, response.statusCode());
    }

    @When("I search for {string}")
    public void searchTypes(String type) {
        response = given()
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
        String imageUrl = response.jsonPath().getString("artObjects[0].webImage.url");
        assertNotNull("Image URL is null or missing", imageUrl);

        Response imageResponse = given()
            .when()
            .get(imageUrl);

        assertEquals("Image is not accessible", 200, imageResponse.getStatusCode());
        assertTrue("Response is not an image", imageResponse.getHeader("Content-Type").startsWith("image/"));
    }
}
