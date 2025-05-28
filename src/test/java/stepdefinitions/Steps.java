package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class Steps {

    private Response response;
    //private final String API_KEY = "YOUR_API_KEY"; // Replace with your actual key
    private final String BASE_URL = "https://www.rijksmuseum.nl/api/en/collection";

    @Given("the search API from Rijksmuseum available")
    public void apiIsAvailable() {
        response = given()
            .baseUri("https://www.rijksmuseum.nl/api/en/collection")
           // .queryParam("key", API_KEY)
            .when()
            .get();
        assertEquals(200, response.statusCode(), "API is not available");
    }

    @When("I search for {string}")
    public void searchTypes(String type) {
        response = given()
            .baseUri("https://www.rijksmuseum.nl/api/en/collection")
           // .queryParam("key", API_KEY)
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
