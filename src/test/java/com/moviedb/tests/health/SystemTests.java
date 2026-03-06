package com.moviedb.tests.health;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import com.moviedb.actions.SystemActions;
import com.moviedb.api.SpecBuilder;

import io.restassured.response.Response;

public class SystemTests {

	@Test(description = "Verify that the health check endpoint returns 200 OK and status 'UP'")
	public void givenAllAPIsAreUp_whenHealthCheckIsPerformed_thenReturn200() {
		Response response = SystemActions.getHealthStatus();

		response.then()
			.spec(SpecBuilder.getResponseSpecification())
			.statusCode(200)
			.body("status", equalTo("UP"));
	}

	@Test(description = "Verify that the Database gets reset and 10 default records are re-seeded back")
	public void testDatabaseReset() {
		Response response = SystemActions.resetDatabase();

		response.then()
			.spec(SpecBuilder.getResponseSpecification())
			.statusCode(200);
	}
}
