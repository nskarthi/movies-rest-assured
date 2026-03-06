package com.moviedb.actions;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.moviedb.api.RestResource;
import com.moviedb.api.endpoints.Routes;
import com.moviedb.pojos.movies.Movie;

import io.restassured.response.Response;

public class MovieActions {
/*    private RequestSpecification requestSpec;

    // Pass the request specification (reqSpec) from the BaseTest into this class
    public MovieActions(RequestSpecification reqSpec) {
        this.requestSpec = reqSpec;
    } */

    public static Response getMovieById(int movieId) {
        return RestResource.performGET("id", movieId, Routes.MOVIE_BY_ID);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .pathParam("id", movieId)
            .when()
                .get(Routes.MOVIE_BY_ID);*/
    }

    public static Response getAllMovies(int page, int limit) {
    	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("page", page);
    	mapParams.put("limit", limit);
        return RestResource.performGET(mapParams, Routes.MOVIES);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .queryParam("page", page)
                .queryParam("limit", limit)
            .when()
                .get(Routes.MOVIES);*/
    }

    public static Response getAllMovies() {
        return RestResource.performGET(Routes.MOVIES);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
            .when()
                .get(Routes.MOVIES);*/
    }
    
    public static Response getMoviesByCountry(String country) {
    	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("country", country);
        return RestResource.performGET(mapParams, Routes.MOVIE_FILTER);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .queryParam("country", country)
            .when()
                .get(Routes.MOVIE_FILTER);*/
    }

    public static Response getMoviesByActor(String actor) {
    	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("actor", actor);
        return RestResource.performGET(mapParams, Routes.MOVIE_FILTER);
 /*       return given()
                .spec(SpecBuilder.getRequestSpecification())
                .queryParam("actor", actor)
            .when()
                .get(Routes.MOVIE_FILTER);*/
    }

    public static Response getMoviesByDirector(String director) {
    	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("director", director);
        return RestResource.performGET(mapParams, Routes.MOVIE_FILTER);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .queryParam("director", director)
            .when()
                .get(Routes.MOVIE_FILTER);*/
    }

    //done
    public static Response getMoviesByTitle(String title) {
    	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("name", title);
        return RestResource.performGET(mapParams, Routes.MOVIE_SEARCH);
/*		return given()
                .spec(SpecBuilder.getRequestSpecification())
                .queryParams(mapParams)
            .when()
                .get(Routes.MOVIE_SEARCH);*/
    }

    public static Response createMovie(Object movieBody) {
    	return RestResource.performPOST(movieBody, Routes.MOVIES);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .body(movieBody)
            .when()
                .post(Routes.MOVIES);*/
    }

    public static Response createMultipleMovies(List<Movie> movies) {
    	return RestResource.performPOST(movies, Routes.MOVIES_BULK);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .body(movieBody)
            .when()
                .post(Routes.MOVIES);*/
    }
    
    public static Response updateMovie(int movieId, Object updateBody) {
    	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("id", movieId);
    	return RestResource.performPUT(mapParams, updateBody, Routes.MOVIE_BY_ID);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .pathParam("id", movieId)
                .body(updateBody)
            .when()
                .put(Routes.MOVIE_BY_ID);*/
    }

    public static Response patchUpdateMovie(int movieId, Object updateBody) {
       	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("id", movieId);
    	return RestResource.performPATCH(mapParams, updateBody, Routes.MOVIE_BY_ID);
/*        return given()
                .spec(SpecBuilder.getRequestSpecification())
                .pathParam("id", movieId)
                .body(updateBody)
            .when()
                .patch(Routes.MOVIE_BY_ID);*/
    }

    public static Response deleteMovie(int movieId) {
    	HashMap<String, Object> mapParams = new HashMap<>();
    	mapParams.put("id", movieId);
        return RestResource.performDELETE(mapParams, Routes.MOVIE_BY_ID);
/*		return given()
				.spec(SpecBuilder.getRequestSpecification())
                .pathParam("id", movieId)
		.when()
			.delete(Routes.MOVIE_BY_ID);*/
    }
    
    //http://localhost:4000/movies/100/poster
    public static Response uploadMoviePoster(String filePath, int movieId) {
    	return RestResource.performMultiPartPOST(movieId, "poster", new File(filePath), 
    			"image/png", Routes.MOVIE_POSTER);
    }
    
}
