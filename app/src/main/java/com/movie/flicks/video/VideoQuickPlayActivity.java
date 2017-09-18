package com.movie.flicks.video;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.movie.flicks.R;
import com.movie.flicks.models.Movie;
import com.movie.flicks.models.Video;
import com.movie.flicks.network.MovieApiRestClient;
import com.movie.flicks.utils.VideoResponseParser;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Activity to quick play videos using {@link YouTubeBaseActivity} and {@link YouTubePlayerView}
 *
 * @author tejalpar
 */
public class VideoQuickPlayActivity extends YouTubeBaseActivity {
    public static String TAG = VideoQuickPlayActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_quick_play_layout);

        Movie movie = getIntent().getExtras().getParcelable(Movie.PARCEL_KEY);

        if (movie != null) {
            fetchVideoDetailsForPopularMovie(movie);
        }
    }

    private void fetchVideoDetailsForPopularMovie(final Movie movie) {
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
        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.video_player);
        YouTubePlayerViewHelper.initializeAndPlayVideo(youTubePlayerView, trailerVideo.key);
    }
}
