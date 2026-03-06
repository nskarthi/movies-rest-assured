package com.utils;

import static org.testng.Assert.assertEquals;

import io.restassured.response.Response;

public class Validator {
	public static void validateStatusCode(Response response, int expected) {
	    assertEquals(response.getStatusCode(), expected);
	}
}
