package com.movie.flicks.adapters;

import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.flicks.R;
import com.movie.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Movie recycler view adapter
 *
 * @author tejalpar
 */
public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int MOVIE_VIEW_TYPE = 0;
    private static final int POPULAR_MOVIE_VIEW_TYPE = 1;

    private List<Movie> movieList;
    private MovieItemClickListener itemClickListener;

    public interface MovieItemClickListener {
        void onMovieItemClickListener (View view, int position);
    }

    public MovieAdapter(ArrayList<Movie> movieList, MovieItemClickListener itemClickListener) {
        this.movieList = movieList;
        this.itemClickListener = itemClickListener;
    }

    public Movie getItemAtPosition(int position) {
        return movieList.get(position);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (movieList.get(position).isPopular()) {
            return POPULAR_MOVIE_VIEW_TYPE;
        }
        return MOVIE_VIEW_TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // inflate view based on viewType
        switch (viewType) {
            case POPULAR_MOVIE_VIEW_TYPE:
                View popularView = inflater.inflate(R.layout.popular_movie_row_layout, parent, false);

                //create viewholder from above view
                viewHolder = new PopularMovieViewHolder(popularView);
                break;

            case MOVIE_VIEW_TYPE:
            default:
                View movieView = inflater.inflate(R.layout.movie_row_layout, parent, false);
                viewHolder = new MovieViewHolder(movieView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case POPULAR_MOVIE_VIEW_TYPE:
                PopularMovieViewHolder popularMovieViewHolder = (PopularMovieViewHolder) holder;
                Picasso.with(popularMovieViewHolder.itemView.getContext())
                        .load(Movie.getAbsoluteMoviePath(movieList.get(position).backDropUrl))
                        .placeholder(R.drawable.placeholder_image)
                        .into(popularMovieViewHolder.backdropImageView);
                break;

            case MOVIE_VIEW_TYPE:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                setupMovieViewHolder(movieViewHolder, position);
            default:

                break;
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView movieTitleText;
        public TextView movieOverviewText;
        public ImageView posterImageView;

        public MovieViewHolder(View rootView) {
            super(rootView);

            rootView.setOnClickListener(this);
            movieTitleText = (TextView) rootView.findViewById(R.id.movie_title);
            movieOverviewText = (TextView) rootView.findViewById(R.id.movie_overview);
            posterImageView = (ImageView) rootView.findViewById(R.id.movie_poster);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onMovieItemClickListener(view, getAdapterPosition());
        }
    }

    public class PopularMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView backdropImageView;

        public PopularMovieViewHolder(View rootView) {
            super(rootView);

            rootView.setOnClickListener(this);
            backdropImageView = (ImageView) rootView.findViewById(R.id.popular_movie_poster);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onMovieItemClickListener(view, getAdapterPosition());
        }
    }

    private void setupMovieViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        switch (holder.itemView.getContext().getResources().getConfiguration().orientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                Picasso.with(holder.itemView.getContext())
                        .load(Movie.getAbsoluteMoviePath(movie.backDropUrl))
                        .placeholder(R.drawable.placeholder_image)
                        .into(holder.posterImageView);
                break;

            default:
                Picasso.with(holder.itemView.getContext())
                        .load(Movie.getAbsoluteMoviePath(movie.posterUrl))
                        .placeholder(R.drawable.placeholder_image)
                        .into(holder.posterImageView);
                break;

        }
        // bind text data
        holder.movieTitleText.setText(movie.title);
        holder.movieOverviewText.setText(movie.overview);
    }
}
