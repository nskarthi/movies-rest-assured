package api.utils;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;

public class AuthManager {
	private static String token;

    // Static function to return the token
    public static String getToken() {
        if (token == null) {
            token = generateNewToken();
        }
        return token;
    }

    private static String generateNewToken() {
        return given()
        	.baseUri("http://localhost:4000")
            .body("{ \"username\": \"admin\", \"password\": \"admin\" }")
            .contentType(ContentType.JSON)
        .when()
        	.post("/auth/login")
    	.then()
    		.extract().path("token");
    }
}
