package com.moviedb.tests;

import org.testng.annotations.BeforeClass;

import com.moviedb.api.SpecBuilder;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseTest {
    protected RequestSpecification requestSpecification;
    protected ResponseSpecification responseSpecification;

    @BeforeClass
    public void setup() {
        // This still returns a NEW object (thread-safe), but AuthManager.getToken() now returns a CACHED string (fast).
        requestSpecification = SpecBuilder.getRequestSpecification();
        responseSpecification = SpecBuilder.getResponseSpecification();
    }

}
