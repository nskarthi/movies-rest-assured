package com.moviedb.tests.system;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import com.moviedb.actions.SystemActions;
import com.moviedb.api.SpecBuilder;
import com.moviedb.listeners.ExtentReportManager;

import api.contants.StatusCode;
import api.utils.LogUtils;
import io.restassured.response.Response;

public class SystemTests {

	@Test(description = "Verify that the health check endpoint returns 200 OK and status 'UP'",
			groups = {"health_check", "regression", "smoke"})
	public void givenAllAPIsAreUp_whenHealthCheckIsPerformed_thenReturn200() {
		ExtentReportManager.log("Step 1: Calling Health Check API");
        LogUtils.getLogger().info("Step 1: Calling Health Check API");

		Response response = SystemActions.getHealthStatus();

		ExtentReportManager.log("Step 2: Validating API response");
        LogUtils.getLogger().info("Step 2: Validating API response");

		response.then()
			.spec(SpecBuilder.getResponseSpecification())
			.statusCode(StatusCode.CODE_200.getCode())
			.body("status", equalTo("UP"));
	}

}
