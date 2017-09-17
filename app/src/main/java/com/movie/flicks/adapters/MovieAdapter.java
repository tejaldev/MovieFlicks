package com.movie.flicks.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.movie.flicks.R;
import com.movie.flicks.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Movie item adapter
 *
 * @author tejalpar
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final int POPULAR_MOVIE_VIEW_TYPE = 1;

    private List<Movie> movieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        super(context, R.layout.movie_row_layout, movieList);
        this.movieList = movieList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder = null;
        Movie currentMovie = movieList.get(position);

        if (convertView == null) {
            holder = new ItemViewHolder();

            switch (getItemViewType(position)) {
                case POPULAR_MOVIE_VIEW_TYPE:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.popular_movie_row_layout, parent, false);
                    holder.posterImageView = (ImageView) convertView.findViewById(R.id.popular_movie_poster);
                    break;

                default:
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_row_layout, parent, false);
                    holder.movieTitleText = (TextView) convertView.findViewById(R.id.movie_title);
                    holder.movieOverviewText = (TextView) convertView.findViewById(R.id.movie_overview);
                    holder.posterImageView = (ImageView) convertView.findViewById(R.id.movie_poster);
                    break;
            }
            convertView.setTag(holder);
        } else {
            // just retrieve holder from tag
            holder = (ItemViewHolder) convertView.getTag();
        }

        // now bind data in holder based on view Type
        if (getItemViewType(position) == POPULAR_MOVIE_VIEW_TYPE) {
            Picasso.with(this.getContext())
                    .load(Movie.getAbsoluteMoviePath(currentMovie.backDropUrl))
                    .placeholder(R.drawable.placeholder_image)
                    .into(holder.posterImageView);
        } else {
            // check orientation and set poster or backdrop url
            switch (getContext().getResources().getConfiguration().orientation) {
                case Configuration.ORIENTATION_LANDSCAPE:
                    Picasso.with(this.getContext())
                            .load(Movie.getAbsoluteMoviePath(currentMovie.backDropUrl))
                            .placeholder(R.drawable.placeholder_image)
                            .into(holder.posterImageView);
                    break;

                default:
                    Picasso.with(this.getContext())
                            .load(Movie.getAbsoluteMoviePath(currentMovie.posterUrl))
                            .placeholder(R.drawable.placeholder_image)
                            .into(holder.posterImageView);
                    break;

            }
            // bind text data
            holder.movieTitleText.setText(currentMovie.title);
            holder.movieOverviewText.setText(currentMovie.overview);
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        // 2 types of views
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isPopular()) {
            return POPULAR_MOVIE_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    private static class ItemViewHolder {
        public TextView movieTitleText;
        public TextView movieOverviewText;
        public ImageView posterImageView;
    }
}
