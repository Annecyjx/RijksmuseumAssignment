import main.java.SearchApiClient;
import io.restassured.response.Response;

public class SearchTest{

  public void testSearchTypePainting(){
    Response response = SearchApiClient.search（“Painting”）;

  }
}
