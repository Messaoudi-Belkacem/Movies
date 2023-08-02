package com.example.movies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.fragments.HomeFragment;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Movie> movies;
    private final HomeFragment fragment;

    public MoviesRecyclerViewAdapter(ArrayList<Movie> movieList, HomeFragment fragment) {
        this.movies = movieList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Log.d("MoviesRecyclerViewAdapter.java", "onBindViewHolderMethod Called");
        // here you put the image of every movie to the recycler view
        String imageUrl = "https://image.tmdb.org/t/p/original/" + movies.get(position).getPoster_path();

        // Load the image using Glide
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.media) // Placeholder image while loading
                .error(R.drawable.baseline_error_24) // Image to display if loading fails
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); // Cache the image automatically

        Glide.with(fragment.getActivity())
                .load(imageUrl)
                .apply(requestOptions)
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
        Log.d("MoviesRecyclerViewAdapter.java", "notifyDataSetChanged method called");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private MaterialCardView card;
        private ImageView thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    fragment.startDetailActivity(movies.get(position));
                }
            });
        }
    }
}
