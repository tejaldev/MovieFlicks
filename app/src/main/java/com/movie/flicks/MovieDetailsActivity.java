package com.movie.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.movie.flicks.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        selectedMovie = getIntent().getExtras().getParcelable(Movie.PARCEL_KEY);
        loadMovieDetails();
    }

    private void loadMovieDetails() {
        TextView titleTextView = (TextView) findViewById(R.id.movie_title);
        TextView overviewTextView = (TextView) findViewById(R.id.movie_overview);
        RatingBar movieRatingBar = (RatingBar) findViewById(R.id.movie_rating);
        ImageView movieBackdropImage = (ImageView) findViewById(R.id.movie_backdrop_image);

        titleTextView.setText(selectedMovie.title);
        overviewTextView.setText(selectedMovie.overview);
        movieRatingBar.setRating((float) selectedMovie.getRating());
        Picasso.with(this)
                .load(Movie.getAbsoluteMoviePath(selectedMovie.backDropUrl))
                .placeholder(R.drawable.placeholder_image)
                .into(movieBackdropImage);
    }
}
