package com.moviedb.tests.system;

import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.BeforeSuite;

import com.moviedb.actions.SystemActions;
import com.moviedb.api.SpecBuilder;
import com.moviedb.listeners.ExtentReportManager;

import io.restassured.response.Response;

public class DatabaseCleanUp {
	@BeforeSuite(alwaysRun = true, 
			description = "Verify that the Database gets reset and 10 default records are re-seeded back")
	public void globalSetUp() {
		ExtentReportManager.log("Step 1: Resetting DB for Regression");
        System.out.println("--- Global Setup: Resetting DB for Regression ---");
		Response response = SystemActions.resetDatabase();

		ExtentReportManager.log("Step 2: Validating API response");
		response.then()
			.spec(SpecBuilder.getResponseSpecification())
			.statusCode(200)
			.body("message", equalTo("Database reset successfully. 10 seed movies re-inserted."));
	}
}
