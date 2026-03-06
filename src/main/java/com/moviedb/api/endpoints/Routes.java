package com.moviedb.api.endpoints;

public class Routes {
    // Resources
    public static final String MOVIES = "/movies";
    public static final String HEALTH = "/health";
	public static final String DBRESET = "/internal/reset";

    // Sub-paths
    public static final String MOVIE_BY_ID = MOVIES + "/{id}";
    public static final String MOVIE_FILTER = MOVIES + "/filter";
    public static final String MOVIE_SEARCH = MOVIES + "/search";
    public static final String MOVIES_BULK = MOVIES + "/bulk";
    public static final String MOVIE_POSTER = MOVIES + "/{id}" + "/poster";

}
