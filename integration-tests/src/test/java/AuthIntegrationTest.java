import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.notNullValue;

public class AuthIntegrationTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://api-gateway.api-gateway.orb.local";

    }

    @Test
    public void shouldReturnOKWithValidToken() {
        String loginPayload = """
                {
                    "email": "babacarthiam@gmail.com",
                    "password": "abc123"
                }
                """;

        Response response = RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token", notNullValue())
                .extract()
                .response();

        System.out.println("Token: " + response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnauthorizedOnInvalidLogin() {
        String loginPayload = """
                {
                    "email": "babacarthiam@email.com",
                    "password": "abc1235"
                }
                """;

        RestAssured.given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);

    }
}
