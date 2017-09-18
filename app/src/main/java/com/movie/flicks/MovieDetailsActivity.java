package com.movie.flicks;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.movie.flicks.models.Movie;
import com.movie.flicks.models.Video;
import com.movie.flicks.network.MovieApiRestClient;
import com.movie.flicks.utils.VideoResponseParser;
import com.movie.flicks.video.YouTubePlayerViewHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieDetailsActivity extends YouTubeBaseActivity {
    public static String TAG = MovieDetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie selectedMovie = getIntent().getExtras().getParcelable(Movie.PARCEL_KEY);

        fetchTrailerVideo(selectedMovie);
        loadMovieDetails(selectedMovie);
    }

    private void loadMovieDetails(Movie selectedMovie) {
        TextView titleTextView = (TextView) findViewById(R.id.movie_title);
        TextView overviewTextView = (TextView) findViewById(R.id.movie_overview);
        RatingBar movieRatingBar = (RatingBar) findViewById(R.id.movie_rating);

        titleTextView.setText(selectedMovie.title);
        overviewTextView.setText(selectedMovie.overview);
        movieRatingBar.setRating((float) selectedMovie.getRating());
    }

    public void fetchTrailerVideo(Movie movie) {
        RequestParams params = new RequestParams();
        params.put("movie_id", movie.movieId);

        MovieApiRestClient.get(
            MovieApiRestClient.buildVideoUrl(MovieApiRestClient.MOVIE_VIDEO_RELATIVE_URL, movie.movieId),
            null, new JsonHttpResponseHandler() {

                    @Override
                     public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Video video = VideoResponseParser.parseVideoDetailsFromJson(response);
                        loadTrailerVideo(video);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Log.e(TAG, "Failed to receive api response: " + responseString, throwable);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.e(TAG, "Failed to receive api response: " + errorResponse, throwable);
                    }
                });
    }

    private void loadTrailerVideo(final Video trailerVideo) {
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.popular_movie_player);
        YouTubePlayerViewHelper.initializeAndCueVideo(youTubePlayerView, trailerVideo.key);
    }
}
