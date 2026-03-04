package com.moviedb.actions;

import static io.restassured.RestAssured.given;

import com.moviedb.api.endpoints.Routes;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SystemActions {
    private RequestSpecification requestSpec;

    // Pass the request specification (reqSpec) from the BaseTest into this class
    public SystemActions(RequestSpecification reqSpec) {
        this.requestSpec = reqSpec;
    }

    public Response getHealthStatus() {
        return given()
                .spec(requestSpec)
            .when()
                .get(Routes.HEALTH);
    }
}
