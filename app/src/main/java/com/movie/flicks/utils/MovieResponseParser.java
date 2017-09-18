package com.movie.flicks.utils;

import android.util.Log;

import com.movie.flicks.models.Movie;
import com.movie.flicks.models.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author tejalpar
 */
public class MovieResponseParser {
    public static String TAG = MovieResponseParser.class.getSimpleName();

    public static ArrayList<Movie> parseMovieInfoFromJson(JSONObject response) {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            JSONArray resultsJson = response.getJSONArray("results");

            for (int i = 0; i < resultsJson.length(); i++) {
                Movie movie = new Movie();
                JSONObject item = resultsJson.getJSONObject(i);

                movie.title = item.optString("title");
                movie.movieId = item.optInt("id");
                movie.posterUrl = item.optString("poster_path");
                movie.backDropUrl = item.optString("backdrop_path");
                movie.overview = item.optString("overview");
                movie.voteAverage = item.optDouble("vote_average");
                movieList.add(movie);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing movie info from json: " + e.getMessage());
        }
        return movieList;
    }

    public static int pareCurrentPageInfo(JSONObject response) {
        return response.optInt("page");
    }
}
