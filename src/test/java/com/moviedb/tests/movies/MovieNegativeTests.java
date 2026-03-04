package com.moviedb.tests.movies;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.Test;

import com.moviedb.actions.MovieActions;
import com.moviedb.api.SpecBuilder;
import com.moviedb.api.endpoints.Routes;
import com.moviedb.pojos.movies.Cast;
import com.moviedb.pojos.movies.Crew;
import com.moviedb.pojos.movies.Finance;
import com.moviedb.pojos.movies.Movie;
import com.moviedb.pojos.movies.ReleaseDetails;

import io.restassured.RestAssured;
import io.restassured.config.HeaderConfig;
import io.restassured.response.Response;

public class MovieNegativeTests {

	@Test(description = "Verify that an unauthorized user cannot access movies when no token is passed")
	public void givenNoToken_whenRequestingMovies_thenReturns401() {
			given(SpecBuilder.getRequestSpecification())
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

		given(SpecBuilder.getRequestSpecification())
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
		int movieId = 200;

        Response response = MovieActions.getMovieById(movieId);
        response.then()
        	.spec(SpecBuilder.getResponseSpecification())
        	.assertThat()
        	.statusCode(404)
			.body("status", equalTo(404),
					"error", equalTo("Not Found"),
					"message", equalTo("Movie with id " + movieId + " not found"));
	}

	@Test(description = "Ensure that a 400 BAD REQUEST is returned when movie title is missing in the movie creation request")
	public void givenInvalidMovieDetails_whenCreatingMovie_thenStatusIs404() {
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

		Movie moviePayloadWithoutTitle = Movie.builder()
			.crew(crew)
			.cast(List.of(cast1, cast2, cast3))
			.genres(genres)
			.releaseDetails(releaseDetails)
			.finance(finance)
			.build();

		Response response = MovieActions.createMovie(moviePayloadWithoutTitle);
		response.then()
			.spec(SpecBuilder.getResponseSpecification())
			.statusCode(400)
			.body("error", equalTo("Bad Request"))
			.body("message", equalTo("Field \"title\" is required and must be a non-empty string"));
	}

	@Test(description = "Ensure that a 404 is returned while attempting to delete a non-existent movie")
	public void givenInvalidMovieId_whenDeletingMovie_thenStatusIs404() {
		int movieId = 100;

		Response response = MovieActions.deleteMovie(movieId);
		response.then()
			.statusCode(404)
			.body("error", equalTo("Not Found"))
			.body("message", equalTo("Movie with id " + movieId + " not found"));
	}

	@Test(description = "Verify that a non-existent movie title cannot be partially updated using PATCH")
	public void patchUpdateMovieTitleFailure() {
	    int movieId = 40;
	    String newMovieTitle = "The Parasite";

	    Map<String, Object> updatePayload = new HashMap<>();
	    updatePayload.put("title", newMovieTitle);

	    MovieActions.patchUpdateMovie(movieId, updatePayload)
	        .then()
	            .spec(SpecBuilder.getResponseSpecification())
	            .statusCode(404)
	            .body("error", equalTo("Not Found"))
	            .body("message", equalTo("Movie with id " + movieId + " not found"));
	}
	
}
