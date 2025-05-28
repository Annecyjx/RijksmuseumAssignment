package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Steps {

    private Response response;
    private final String BASE_URL = "https://www.rijksmuseum.nl/api/en/collection";

    @Given("the search API from Rijksmuseum available")
    public void apiIsAvailable() {
        response = given()
            .when()
            .get(BASE_URL);
        assertEquals(200, response.statusCode(), "API is not available");
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
        assertEquals(200, response.getStatusCode(), "API did not return 200 OK");
        assertTrue(response.getBody().asString().contains("artObjects"), "No artObjects found in response");
    }
  
    @Then("I should receive a successful response with no results")
    public void validateNoResults() {
        assertEquals(200, response.getStatusCode());
        assertTrue(response.asString().contains("\"artObjects\":[]"));
    }
}
