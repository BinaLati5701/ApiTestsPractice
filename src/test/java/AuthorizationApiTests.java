import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthorizationApiTests {
    @Test
    public void loginTest() {

        // request body
        String requestBody = "{"
                + "\"username\": \"emilys\","
                + "\"password\": \"emilyspass\","
                + "\"expiresInMins\": 30"
                + "}";

        // send POST request
        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("https://dummyjson.com/auth/login");

        // print response
        response.prettyPrint();

        // validate status code
        response.then().statusCode(200);

        // convert response to string
        String responseBody = response.getBody().asString();

        // validations
        assertTrue(responseBody.contains("accessToken"));

        assertTrue(responseBody.contains("refreshToken"));

        assertTrue(responseBody.contains("emilys"));

        assertTrue(responseBody.contains("Emily"));

        assertTrue(responseBody.contains("Johnson"));
    }
}
