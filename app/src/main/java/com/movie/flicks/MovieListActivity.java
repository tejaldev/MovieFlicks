package com.movie.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.movie.flicks.adapters.MovieAdapter;
import com.movie.flicks.listeners.EndlessRecyclerViewScrollListener;
import com.movie.flicks.models.Movie;
import com.movie.flicks.network.MovieApiRestClient;
import com.movie.flicks.utils.MovieResponseParser;
import com.movie.flicks.video.VideoQuickPlayActivity;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity implements MovieAdapter.MovieItemClickListener {
    public static String TAG = MovieListActivity.class.getSimpleName();

    private int currentPage;
    private MovieAdapter itemAdapter;
    private RecyclerView movieRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        movieRecyclerView = (RecyclerView) findViewById(R.id.movie_recycler_view);
        movieRecyclerView.setLayoutManager(layoutManager);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // triggers when new data needs to be appended
                loadNowPlayingMoviesNextPage(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        movieRecyclerView.addOnScrollListener(scrollListener);

        // first time load
        loadNowPlayingMovies();
    }

    @Override
    public void onMovieItemClickListener(View view, int position) {
        launchMovieDetailActivity(itemAdapter.getItemAtPosition(position));
    }

    @Override
    public void onPopularMovieItemClickListener(View view, int position) {
        launchVideoQuickPlayActivity(itemAdapter.getItemAtPosition(position));
    }

    // ***************** API calls start ***************** //

    public void loadNowPlayingMovies() {
        MovieApiRestClient.get(MovieApiRestClient.NOW_PLAYING_RELATIVE_URL, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                setItemAdapter(MovieResponseParser.parseMovieInfoFromJson(response));
                currentPage = MovieResponseParser.pareCurrentPageInfo(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "Failed to receive api response: " + responseString, throwable);
            }
        });
    }

    public void loadNowPlayingMoviesNextPage(int offset) {
        RequestParams params = new RequestParams();
        params.put("page", currentPage + offset);

        MovieApiRestClient.get(MovieApiRestClient.NOW_PLAYING_RELATIVE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                insertNewPageData(MovieResponseParser.parseMovieInfoFromJson(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "Failed to receive api response: " + responseString, throwable);
            }
        });
    }

    // ***************** API calls end ***************** //

    private void setItemAdapter(final ArrayList<Movie> movieList) {
        itemAdapter = new MovieAdapter(movieList, this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                movieRecyclerView.setAdapter(itemAdapter);
            }
        });
    }

    private void insertNewPageData(final ArrayList<Movie> movieList) {
        itemAdapter.setMoreData(movieList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                itemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void launchMovieDetailActivity(Movie movie) {
        Intent intent = new Intent(MovieListActivity.this, MovieDetailsActivity.class);
        intent.putExtra(Movie.PARCEL_KEY, movie);
        startActivity(intent);
    }

    private void launchVideoQuickPlayActivity(Movie movie) {
        Intent intent = new Intent(MovieListActivity.this, VideoQuickPlayActivity.class);
        intent.putExtra(Movie.PARCEL_KEY, movie);
        startActivity(intent);
    }
}
