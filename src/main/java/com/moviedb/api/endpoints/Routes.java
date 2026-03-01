package com.moviedb.api.endpoints;

public class Routes {
    // Resources
    public static final String MOVIES = "/movies";
    public static final String HEALTH = "/health";
    
    // Dynamic Resources (Example)
    public static String movieById(int id) {
        return MOVIES + "/" + id;
    }
}
