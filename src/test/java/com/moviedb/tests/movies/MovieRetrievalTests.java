package com.moviedb.tests.movies;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.moviedb.actions.MovieActions;
import com.moviedb.api.SpecBuilder;
import com.moviedb.pojos.movies.Movie;
import com.moviedb.pojos.movies.MoviesList;
import com.moviedb.pojos.movies.MoviesSearch;

import io.restassured.response.Response;

public class MovieRetrievalTests {

	public void assertMovieEquals(Movie responseMovie, Movie requestMovie) {
		
	}

	@Test(description = "Verify that a unique movie title returns exactly one movie")
	public void searchByMovieTitleReturnsSingleExactMatch() {
		String movieTitle = "RRR";
		Response response = MovieActions.getMoviesByTitle(movieTitle);

		MoviesSearch moviesSearchResponse = response.then()
				.spec(SpecBuilder.getResponseSpecification())
				.statusCode(200)
				.extract()
				.as(MoviesSearch.class);

		assertEquals(moviesSearchResponse.getTotal(), 1, "Expected exactly one movie");
		assertEquals(moviesSearchResponse.getData().get(0).getTitle(), movieTitle, "Movie title does not match");
	}

	@Test(description = "Verify search returns all matching movies for a common movie title query")
	public void searchByMovieTitleReturnsAllPartialTitleMatchingMovies() {
		String movieTitle = "a";
		Response response = MovieActions.getMoviesByTitle(movieTitle);
		
		MoviesSearch moviesSearchResponse = response.then()
				.spec(SpecBuilder.getResponseSpecification())
				.statusCode(200)
				.extract()
				.as(MoviesSearch.class);

		assertTrue(moviesSearchResponse.getTotal() > 1, "Expected multiple movies for common search term");
		assertTrue(moviesSearchResponse.getData().get(0).getTitle().contains(movieTitle), "Movie title does not contain search parameter");
	}

    @Test(description = "Verify that the expected movie returns")
    public void searchByMovieIdReturnsSingleExactMatch() {
        Response response = MovieActions.getMovieById(2);

        Movie movieResponse = response.then()
        	.spec(SpecBuilder.getResponseSpecification())
        	.statusCode(200)
        	.extract()
        	.as(Movie.class);

        assertTrue(movieResponse.getTitle().equals("Inception"), "Movie title mismatch");
    }
    
    @Test(description = "Verify that the movie list is correctly paginated")
    public void listMoviesWithPagination() {
        int page = 1;
        int limit = 100;

        Response response = MovieActions.getAllMovies(page, limit);
        
        MoviesList moviesListResponse = response.then()
            .spec(SpecBuilder.getResponseSpecification())
            .extract()
            .as(MoviesList.class);

        assertEquals(moviesListResponse.getPage(), page, "MoviesList [Page] mismatch");
        assertEquals(moviesListResponse.getLimit(), limit, "MoviesList [limit] mismatch");
    }

    @Test(description = "Verify movie list with default pagination")
    public void listMoviesWithDefaultPagination() {
        Response response = MovieActions.getAllMovies();
        
        MoviesList moviesListResponse = response.then()
            .spec(SpecBuilder.getResponseSpecification())
            .extract()
            .as(MoviesList.class);

        assertEquals(moviesListResponse.getPage(), 1, "MoviesList [Page] mismatch");
        assertEquals(moviesListResponse.getLimit(), 10, "MoviesList [limit] mismatch");
    }
}
