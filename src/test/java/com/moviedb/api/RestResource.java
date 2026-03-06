package com.moviedb.api;

import static io.restassured.RestAssured.given;

import java.io.File;
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

	public static Response performMultiPartPOST(int pathParamValue, String controlName, File posterFile, String mimeType, String route) {
        // 'poster' is the control name (-F 'poster=...')
        // 'image/png' is the mimeType specified in the curl command
		// Sample request
		//  'http://localhost:4000/movies/100/poster' \
		//  -H 'accept: */*' \
		//  -H 'Content-Type: multipart/form-data' \
		//  -F 'poster=@Screenshot.png;type=image/png'
		return given()
				.spec(SpecBuilder.getRequestSpecification())
	            .contentType("multipart/form-data")
				.header("accept", "*/*")
				.multiPart(controlName, posterFile, mimeType)
		.when()
        	// Rest Assured replaces {id} with the movieId automatically
			.post(route, pathParamValue);
	}

}
