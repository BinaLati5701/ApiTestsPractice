import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CurrentUserApiTests {

    // reusable base url
    String baseUrl = "https://dummyjson.com";

    // method returns token
    public String getToken() {

        // endpoint
        String endpoint = "/auth/login";

        // request body
        String requestBody = "{"
                + "\"username\": \"emilys\","
                + "\"password\": \"emilyspass\","
                + "\"expiresInMins\": 30"
                + "}";

        // login request
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)

                .when()
                .post(baseUrl + endpoint);

        // extract token from response
        return response.jsonPath().getString("accessToken");
    }

    @Test
    public void getCurrentUserWithToken() {

        // endpoint
        String endpoint = "/auth/me";

        // call method and store token
        String token = getToken();

        // authenticated request
        Response response = RestAssured
                .given()
                .header("Authorization", "Bearer " + token)

                .when()
                .get(baseUrl + endpoint);

        // print response
        response.prettyPrint();

        // validate status code
        response.then().statusCode(200);

        // validations
        String responseBody = response.getBody().asString();

        assertTrue(responseBody.contains("Emily"));

        assertTrue(responseBody.contains("Johnson"));

        assertTrue(responseBody.contains("emilys"));
    }
}