import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductsApiTests {

    // reusable base url
    String baseUrl = "https://dummyjson.com";

    @Test
    public void getAllProducts() {

        // endpoint
        String endpoint = "/products";

        // send GET request
        Response response = RestAssured
                .given()
                .when()
                .get(baseUrl + endpoint);

        // print response
        response.prettyPrint();

        // validate status code
        response.then().statusCode(200);

        // convert response to string
        String responseBody = response.getBody().asString();

        // validations
        assertTrue(responseBody.contains("products"));

        assertTrue(responseBody.contains("beauty"));

        assertTrue(responseBody.contains("Essence Mascara Lash Princess"));
    }

    @Test
    public void addNewProduct() {

        // endpoint
        String endpoint = "/products/add";

        // request body
        String requestBody = "{"
                + "\"title\": \"Test Product\","
                + "\"price\": 100"
                + "}";

        // send POST request
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)

                .when()
                .post(baseUrl + endpoint);

        // print response
        response.prettyPrint();

        // validate status code
        response.then().statusCode(201);

        // convert response to string
        String responseBody = response.getBody().asString();

        // validations
        assertTrue(responseBody.contains("Test Product"));

        assertTrue(responseBody.contains("100"));

        assertTrue(responseBody.contains("id"));
    }
}