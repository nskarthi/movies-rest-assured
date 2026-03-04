package com.moviedb.tests;

import org.testng.annotations.BeforeClass;

import com.moviedb.actions.MovieActions;
import com.moviedb.api.SpecBuilder;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseTest {
    protected RequestSpecification requestSpecification;
    protected ResponseSpecification responseSpecification;
    protected MovieActions movieActions;

    @BeforeClass
    public void setup() {
        requestSpecification = SpecBuilder.getRequestSpecification();
        responseSpecification = SpecBuilder.getResponseSpecification();

        //movieActions = new MovieActions(requestSpecification);
    }

}
