package com.moviedb.actions;

import static io.restassured.RestAssured.given;

import com.moviedb.api.SpecBuilder;
import com.moviedb.api.endpoints.Routes;

import io.restassured.response.Response;

public class SystemActions {

    public static Response getHealthStatus() {
        return given()
                .spec(SpecBuilder.getRequestSpecification())
            .when()
                .get(Routes.HEALTH);
    }

    public static Response resetDatabase() {
        return given()
                .spec(SpecBuilder.getRequestSpecification())
            .when()
                .delete(Routes.DBRESET);
    }
}
