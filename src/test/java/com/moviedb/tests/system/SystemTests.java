package com.moviedb.tests.system;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import com.moviedb.actions.SystemActions;
import com.moviedb.api.SpecBuilder;
import com.moviedb.listeners.ExtentReportManager;

import io.restassured.response.Response;

public class SystemTests {

	@Test(description = "Verify that the health check endpoint returns 200 OK and status 'UP'")
	public void givenAllAPIsAreUp_whenHealthCheckIsPerformed_thenReturn200() {
		ExtentReportManager.log("Step 1: Calling Health Check API");
		Response response = SystemActions.getHealthStatus();

		ExtentReportManager.log("Step 2: Validating API response");
		response.then()
			.spec(SpecBuilder.getResponseSpecification())
			.statusCode(200)
			.body("status", equalTo("UP"));
	}

}
