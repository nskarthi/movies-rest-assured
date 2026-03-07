package api.utils;

import static io.restassured.RestAssured.given;

import java.time.Instant;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AuthManager {
	private static String cachedToken;
    private static Instant expiryTime;

    public static void clearCache() {
        synchronized (AuthManager.class) {
            System.out.println("[AUTH] Clearing Token Cache...");
            cachedToken = null;
            expiryTime = null;
        }
    }

    // Static function to return the cachedToken
    public synchronized static String getToken() {
        boolean needsRefresh = (cachedToken == null || Instant.now().isAfter(expiryTime.minusSeconds(60)));

        if (needsRefresh) {
            System.out.println("[AUTH] Token missing/expired. Generating new session...");
            cachedToken = generateNewToken();
            expiryTime = getExpiryFromJWT(cachedToken);
            System.out.println("[AUTH] New Token Generated. Expires at: " + expiryTime);
        }
        return cachedToken;
    }

    private static String generateNewToken() {
        String baseUri = ConfigLoader.getInstance().getBaseUrl();
        String username = ConfigLoader.getInstance().getUserName();
        String password = ConfigLoader.getInstance().getPassword();
        
        Response response = given()
                .baseUri(baseUri)
                //example: { "username": "admin", "password": "admin" }
                .body("{ \"username\": \"" + username + "\", \"password\": \"" + password + "\" }")
                .contentType(ContentType.JSON)
				.log().all()
            .when()
                .post("/auth/login");

        if (response.getStatusCode() != 200) {
            throw new RuntimeException("Failed to login! Status: " + response.getStatusCode() + 
                                       " Body: " + response.getBody().asString());
        }

        return response.path("token");
    }

    private static Instant getExpiryFromJWT(String token) {
        try {
            // parts[0]=Header, parts[1]=Payload, parts[2]=Signature
            String[] parts = token.split("\\.");
            byte[] decodedBytes = java.util.Base64.getUrlDecoder().decode(parts[1]);
            long expSeconds = io.restassured.path.json.JsonPath.from(new String(decodedBytes)).getLong("exp");
            return Instant.ofEpochSecond(expSeconds);
        } catch (Exception e) {
            System.err.println("Could not parse JWT Expiry. Defaulting to 1 hour from now.");
            return Instant.now().plusSeconds(3600);
        }
    }
}
