package com.moviedb.api;

import api.utils.AuthManager;
import api.utils.ConfigLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
	public static RequestSpecification getRequestSpecification() {
	    String baseUri = ConfigLoader.getInstance().getBaseUrl(); // Pull from properties
		String bearerToken = AuthManager.getToken();
		RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
				.setBaseUri(baseUri)
				.addHeader("Authorization", "Bearer " + bearerToken)
				.setContentType(ContentType.JSON)
				.log(LogDetail.ALL);

		// 3. Enable Global Logging on Failure
		// Shortcut
	    //RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		// RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig()
		//	    .enableLoggingOfRequestAndResponseIfValidationFails()
		//	    .blacklistHeader("Authorization"));
		
		return requestSpecBuilder.build();
	}

	public static ResponseSpecification getResponseSpecification() {
		ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
				.expectContentType(ContentType.JSON)
				.log(LogDetail.ALL);
		return responseSpecBuilder.build();
	}
}
