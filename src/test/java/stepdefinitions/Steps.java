package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Steps {

    private Response response;

    @Given("the search API of Rijksmuseum API available")
    public void apiIsAvailable() {
        response = given()
            .baseUri("https://www.rijksmuseum.nl/api/en/collection")
            .queryParam("key", "?")
            .when()
            .get();
        assertEquals(200, response.statusCode(), "API is not available");
    }

    @When("I search for {string}")
    public void searchTypes(String type) {
        response = given()
            .baseUri("https://www.rijksmuseum.nl/api/en/collection")
            .queryParam("key", "?")
            .queryParam("q", type)
            .when()
            .get();
    }

    @Then("I should receive a successful response")
    public void validateSearchResults() {
        assertEquals(200, response.statusCode());
        assertTrue(response.getBody().asString().contains("artObjects"));
    }
  
    @Then("I should receive a not found response")
    public void validateSearchResults() {
        assertEquals(404, response.statusCode());
        assertTrue(response.getBody().asString().contains("artObjects"));
    }
}
