package com.moviedb.tests.movies;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;

import org.testng.annotations.Test;

import com.moviedb.api.endpoints.Routes;
import com.moviedb.pojos.movies.Cast;
import com.moviedb.pojos.movies.Crew;
import com.moviedb.pojos.movies.Finance;
import com.moviedb.pojos.movies.Movie;
import com.moviedb.pojos.movies.ReleaseDetails;
import com.moviedb.tests.BaseTest;

import io.restassured.RestAssured;
import io.restassured.config.HeaderConfig;

public class MovieNegativeTests extends BaseTest {

	@Test(description = "Verify that an unauthorized user cannot access movies when no token is passed")
	public void givenNoToken_whenRequestingMovies_thenReturns401() {
		int qParamPage = 1;
		int qParamLimit = 100;

		given(requestSpecification)
			.queryParam("page", qParamPage)
			.queryParam("limit", qParamLimit)
		    .config(RestAssured.config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("Authorization")))
		    .header("Authorization", "") // This will now replace the global token
		.when()
			.get(Routes.MOVIES)
		.then()
			.statusCode(401);
	}

	@Test(description = "Verify that an unauthorized user cannot access movies when invalid token is passed")
	public void givenInvalidToken_whenRequestingMovies_thenReturns401() {
		int qParamPage = 1;
		int qParamLimit = 100;

		given(requestSpecification)
			.queryParam("page", qParamPage)
			.queryParam("limit", qParamLimit)
		    .config(RestAssured.config().headerConfig(HeaderConfig.headerConfig().overwriteHeadersWithName("Authorization")))
		    .header("Authorization", "Bearer SPECIAL_TEST_TOKEN") // This will now replace the global token
		.when()
			.get(Routes.MOVIES)
		.then()
			.statusCode(401);
	}

	@Test(description = "Ensure that 404 NOT FOUND is returned when an invalid movie Id is requested")
	public void givenInvalidMovieId_whenRequestingMovieInfo_thenStatusIs404() {
		String movieId = "200";
		given(requestSpecification)
		.when()
			.get(Routes.MOVIES + "/" + movieId)
		.then()
			.spec(responseSpecification)
			.assertThat()
			.statusCode(404)
			.body("status", equalTo(404),
					"error", equalTo("Not Found"),
					"message", equalTo("Movie with id " + movieId + " not found"));
	}

	@Test(description = "Ensure that a 404 BAD REQUEST is returned when movie title is missing in the movie creation request")
	public void givenInvalidMovieDetails_whenCreatingMovie_thenStatusIs400() {
		String movieTitle = "Boston Boys";
		Crew crew = new Crew("Christopher Nolan", "Hans Zimmer", "Syncopy, Lynda Obst Productions, Legendary Pictures");
		Cast cast1 = new Cast("Matthew McConaughey", "Joseph Cooper", List.of(
		        "Saturn Award for Best Actor (Nominee)", 
		        "Critics' Choice Movie Award for Best Actor (Nominee)"
		    ));
		Cast cast2 = new Cast("Anne Hathaway", "Anne Hathaway", List.of(
		        "Saturn Award for Best Actress (Nominee)"));
		Cast cast3 = new Cast("Jessica Chastain", "Murphy Cooper (Adult)", List.of(
		        "Saturn Award for Best Supporting Actress (Nominee)"));
		List<String> genres = List.of("Sci-Fi", "Adventure", "Drama");
		ReleaseDetails releaseDetails= new ReleaseDetails("English", "United States, United Kingdom");
		Finance finance = new Finance(165000000L, 773800000L, 47200000L, "USD");

		Movie moviePayloadWithoutTitle = new Movie()
			.setCrew(crew)
			.setCast(List.of(cast1, cast2, cast3))
			.setGenres(genres)
			.setReleaseDetails(releaseDetails)
			.setFinance(finance);

		given(requestSpecification)
			.body(moviePayloadWithoutTitle)
		.when()
			.post("")
		.then()
			.spec(responseSpecification)
			.statusCode(404);
	}
	
	@Test(description = "Ensure that a 404 is returned when authenticated user attempts to delete a non-existent movie")
	public void givenInvalidMovieId_whenDeletingMovie_thenStatusIs404() {
		String movieId = "100";

		given(requestSpecification)
		.when()
			.delete(Routes.MOVIES + "/" + movieId)
		.then()
			.spec(responseSpecification)
			.statusCode(404);
	}
	
}

