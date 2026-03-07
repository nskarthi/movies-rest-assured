package com.moviedb.tests.movies;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.moviedb.actions.MovieActions;
import com.moviedb.api.SpecBuilder;
import com.moviedb.listeners.ExtentReportManager;
import com.moviedb.pojos.movies.Movie;
import com.moviedb.pojos.movies.MoviesList;
import com.moviedb.pojos.movies.MoviesSearch;

import api.contants.StatusCode;
import api.utils.LogUtils;
import io.restassured.response.Response;

public class MovieRetrievalTests {

	public void assertMovieEquals(Movie responseMovie, Movie requestMovie) {
		
	}

	@Test(description = "Verify that a unique movie title returns exactly one movie",
			groups = {"movie_retrieval", "regression", "smoke"})
	public void searchByMovieTitleReturnsSingleExactMatch() {
		String movieTitle = "RRR";
        ExtentReportManager.log("Step 1: MovieActions.getMoviesByTitle(movieTitle)");
        LogUtils.getLogger().info("Step 1: MovieActions.getMoviesByTitle(movieTitle)");

		Response response = MovieActions.getMoviesByTitle(movieTitle);

        ExtentReportManager.log("Step 2: Validating API response");
        LogUtils.getLogger().info("Step 2: Validating API response");

		MoviesSearch moviesSearchResponse = response.then()
				.spec(SpecBuilder.getResponseSpecification())
				.statusCode(StatusCode.CODE_200.getCode())
				.extract()
				.as(MoviesSearch.class);

		assertEquals(moviesSearchResponse.getTotal(), 1, "Expected exactly one movie");
		assertEquals(moviesSearchResponse.getData().get(0).getTitle(), movieTitle, "Movie title does not match");
	}

	@Test(description = "Verify search returns all matching movies for a common movie title query",
			groups = {"movie_retrieval", "regression", "smoke"})
	public void searchByMovieTitleReturnsAllPartialTitleMatchingMovies() {
		String movieTitle = "a";
        ExtentReportManager.log("Step 1: MovieActions.getMoviesByTitle(movieTitle)");
        LogUtils.getLogger().info("Step 1: MovieActions.getMoviesByTitle(movieTitle)");

		Response response = MovieActions.getMoviesByTitle(movieTitle);
		
        ExtentReportManager.log("Step 2: Validating API response");
        LogUtils.getLogger().info("Step 2: Validating API response");

		MoviesSearch moviesSearchResponse = response.then()
				.spec(SpecBuilder.getResponseSpecification())
				.statusCode(StatusCode.CODE_200.getCode())
				.extract()
				.as(MoviesSearch.class);

		assertTrue(moviesSearchResponse.getTotal() > 1, "Expected multiple movies for common search term");
		assertTrue(moviesSearchResponse.getData().get(0).getTitle().contains(movieTitle), "Movie title does not contain search parameter");
	}

    @Test(description = "Verify that the expected movie returns",
			groups = {"movie_retrieval", "regression"})
    public void searchByMovieIdReturnsSingleExactMatch() {
        ExtentReportManager.log("Step 1: MovieActions.getMovieById(2)");
        LogUtils.getLogger().info("Step 1: MovieActions.getMovieById(2)");

        Response response = MovieActions.getMovieById(2);

        ExtentReportManager.log("Step 2: Validating API response");
        LogUtils.getLogger().info("Step 2: Validating API response");

        Movie movieResponse = response.then()
        	.spec(SpecBuilder.getResponseSpecification())
        	.statusCode(StatusCode.CODE_200.getCode())
        	.extract()
        	.as(Movie.class);

        assertTrue(movieResponse.getTitle().equals("Inception"), "Movie title mismatch");
    }
    
    @Test(description = "Verify that the movie list is correctly paginated",
			groups = {"movie_retrieval", "regression"})
    public void listMoviesWithPagination() {
        int page = 1;
        int limit = 100;
        ExtentReportManager.log("Step 1: MovieActions.getAllMovies(page, limit)");
        LogUtils.getLogger().info("Step 1: MovieActions.getAllMovies(page, limit)");

        Response response = MovieActions.getAllMovies(page, limit);

        ExtentReportManager.log("Step 2: Validating API response");
        LogUtils.getLogger().info("Step 2: Validating API response");

        MoviesList moviesListResponse = response.then()
            .spec(SpecBuilder.getResponseSpecification())
            .extract()
            .as(MoviesList.class);

        assertEquals(moviesListResponse.getPage(), page, "MoviesList [Page] mismatch");
        assertEquals(moviesListResponse.getLimit(), limit, "MoviesList [limit] mismatch");
    }

    @Test(description = "Verify movie list with default pagination",
			groups = {"movie_retrieval", "regression"})
    public void listMoviesWithDefaultPagination() {
        ExtentReportManager.log("Step 1: MovieActions.getAllMovies()");
        LogUtils.getLogger().info("Step 1: MovieActions.getAllMovies()");

        Response response = MovieActions.getAllMovies();
        
        ExtentReportManager.log("Step 2: Validating API response");
        LogUtils.getLogger().info("Step 2: Validating API response");

        MoviesList moviesListResponse = response.then()
            .spec(SpecBuilder.getResponseSpecification())
            .extract()
            .as(MoviesList.class);

        assertEquals(moviesListResponse.getPage(), 1, "MoviesList [Page] mismatch");
        assertEquals(moviesListResponse.getLimit(), 10, "MoviesList [limit] mismatch");
    }
}
