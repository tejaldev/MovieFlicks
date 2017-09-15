package com.movie.flicks;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.movie.flicks.adapters.MovieAdapter;
import com.movie.flicks.models.Movie;
import com.movie.flicks.network.MovieApiRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {
    public static String TAG = MovieListActivity.class.getSimpleName();

    private ListView listView;
    private Context context;
    private MovieAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        context = this;
        listView = (ListView) findViewById(R.id.movie_list);

        MovieApiRestClient.get("now_playing", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // You should obtain a new client token often, at least as often as your app restarts.

                ArrayList<Movie> movieList = new ArrayList<>();
                try {
                    JSONArray resultsJson = response.getJSONArray("results");

                    for (int i=0; i < resultsJson.length(); i++) {
                        Movie movie = new Movie();
                        JSONObject item = resultsJson.getJSONObject(i);
                        movie.title = item.optString("title");
                        movie.posterUrl = item.optString("poster_path");
                        movie.backDropUrl = item.optString("backdrop_path");
                        movie.overview = item.optString("overview");
                        movieList.add(movie);
                    }
                    setItemAdapter(movieList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, "Failed to receive api response: " + responseString, throwable);
            }
        });
    }

    private void setItemAdapter(final ArrayList<Movie> movieList) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                itemAdapter = new MovieAdapter(context, movieList);
                itemAdapter.notifyDataSetChanged();
                listView.setAdapter(itemAdapter);
            }
        });
    }
}
