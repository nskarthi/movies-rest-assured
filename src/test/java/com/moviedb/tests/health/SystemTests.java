package com.moviedb.tests.health;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.Test;

import com.moviedb.api.endpoints.Routes;
import com.moviedb.pojos.health.Health;
import com.moviedb.tests.BaseTest;

public class SystemTests extends BaseTest {

	@Test(description = "Verify that the health check endpoint returns 200 OK and status 'UP'")
	public void givenAllAPIsAreUp_whenHealthCheckIsPerformed_thenReturn200() {
		Health health = given(requestSpecification)
	.	when()
			.get(Routes.HEALTH)
		.then()
			.spec(responseSpecification)
			.statusCode(200)
			.body("status", equalTo("UP"))
			.extract()
			.as(Health.class);

		System.out.println("Health check status: " + health.getStatus());
	}

}
