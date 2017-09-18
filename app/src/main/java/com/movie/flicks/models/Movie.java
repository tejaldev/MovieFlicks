package com.movie.flicks.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Movie data model
 *
 * @author tejalpar
 */
public class Movie implements Parcelable {
    public static final String BASE_MOVIE_PATH = "https://image.tmdb.org/t/p/w500";
    public static final String PARCEL_KEY = "MOVIE_DATA";

    private static double VOTE_AVG_MIN = 1;
    private static double VOTE_AVG_MAX = 10;

    private static double RATING_MIN = 1;
    private static double RATING_MAX = 5;

    public int movieId;
    public String title;
    public String overview;
    public String posterUrl;
    public String backDropUrl;
    public double voteAverage;

    public Video trailerVideo;

    public Movie() {}

    public Movie(Parcel source) {
        // read values from parcel
        readFromParcel(source);
    }

    public static String getAbsoluteMoviePath(String relativePath) {
        return BASE_MOVIE_PATH + relativePath;
    }

    public boolean isPopular() {
        return  (voteAverage > 5.0);
    }

    public double getRating() {
        return (voteAverage - VOTE_AVG_MIN) / (VOTE_AVG_MAX - VOTE_AVG_MIN) * (RATING_MAX - RATING_MIN) + RATING_MIN;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(posterUrl);
        dest.writeString(backDropUrl);
        dest.writeDouble(voteAverage);
    }

    private void readFromParcel(Parcel source) {
        // read in same order as written
        movieId = source.readInt();
        title = source.readString();
        overview = source.readString();
        posterUrl = source.readString();
        backDropUrl = source.readString();
        voteAverage = source.readDouble();
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {

        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };
}
