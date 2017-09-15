package com.movie.flicks.models;

/**
 * Created by tejalpar on 9/13/17.
 */
public class Movie {
    public static final String BASE_MOVIE_PATH = "https://image.tmdb.org/t/p/w500";

    public String title;
    public String posterUrl;
    public String overview;
    public String backDropUrl;

    public static String getAbsoluteMoviePath(String relativePath) {
        return BASE_MOVIE_PATH + relativePath;
    }
}
