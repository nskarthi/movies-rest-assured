package com.moviedb.api;

import static io.restassured.RestAssured.given;

import java.util.HashMap;

import io.restassured.response.Response;

public class RestResource {

	public static Response performGET(HashMap<String, Object> mapParams, String route) {
		return given()
                .spec(SpecBuilder.getRequestSpecification())
                .queryParams(mapParams)
            .when()
                .get(route);
	}

	public static Response performGET(String pathParamKey, int pathParamValue, String route) {
		return given()
                .spec(SpecBuilder.getRequestSpecification())
                .pathParams(pathParamKey, pathParamValue)
            .when()
                .get(route);
	}
	
	public static Response performGET(String route) {
		return given()
                .spec(SpecBuilder.getRequestSpecification())
            .when()
                .get(route);
	}

	public static Response performPOST(Object payload, String route) {
        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .body(payload)
            .when()
                .post(route);
	}

	public static Response performPUT(HashMap<String, Object> mapParams, Object updateBody, String route) {
        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .pathParams(mapParams)
                .body(updateBody)
            .when()
                .put(route);
	}

	public static Response performPATCH(HashMap<String, Object> mapParams, Object updateBody, String route) {
		return given()
                .spec(SpecBuilder.getRequestSpecification())
                .pathParams(mapParams)
                .body(updateBody)
            .when()
                .patch(route);
	}

	public static Response performDELETE(HashMap<String, Object> mapParams, String route) {
		return given()
				.spec(SpecBuilder.getRequestSpecification())
                .pathParams(mapParams)
		.when()
			.delete(route);
	}
	
}
