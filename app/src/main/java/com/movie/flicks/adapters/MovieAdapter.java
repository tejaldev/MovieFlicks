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

    private List<Movie> movieList;

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        super(context, R.layout.movie_row_layout, movieList);
        this.movieList = movieList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemViewHolder holder = null;

        if (convertView == null) {
            /* means create an item from inflater */

            // inflate layout for convertView
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.movie_row_layout, parent, false);

            // populate holder & add it as a tag to convertView to be used later
            holder = new ItemViewHolder();
            holder.movieTitleText = (TextView) convertView.findViewById(R.id.movie_title);
            holder.movieOverviewText = (TextView) convertView.findViewById(R.id.movie_overview);
            holder.posterImageView = (ImageView) convertView.findViewById(R.id.movie_poster);
            convertView.setTag(holder);

        } else {
            // just retrieve holder from tag
            holder = (ItemViewHolder) convertView.getTag();
        }

        // now bind data to view
        holder.movieTitleText.setText(movieList.get(position).title);
        holder.movieOverviewText.setText(movieList.get(position).overview);

        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // load image
            Picasso.with(this.getContext()).load(Movie.getAbsoluteMoviePath(movieList.get(position).backDropUrl)).resize(500, 300).into(holder.posterImageView);
        } else {
            Picasso.with(this.getContext()).load(Movie.getAbsoluteMoviePath(movieList.get(position).posterUrl)).resize(200, 300).into(holder.posterImageView);
        }

        return convertView;
    }

    private static class ItemViewHolder {
        public TextView movieTitleText;
        public TextView movieOverviewText;
        public ImageView posterImageView;
    }
}
