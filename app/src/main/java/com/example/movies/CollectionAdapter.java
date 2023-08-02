package com.example.movies;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.movies.activities.DetailActivity;
import com.example.movies.fragments.AboutMovieFragment;
import com.example.movies.fragments.CastFragment;
import com.example.movies.fragments.ReviewsFragment;

public class CollectionAdapter extends FragmentStateAdapter {

    private Movie movie;

    public CollectionAdapter(@NonNull DetailActivity detailActivity) {
        super(detailActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            AboutMovieFragment aboutMovieFragment = new AboutMovieFragment();
            Bundle args = new Bundle();
            args.putSerializable(AboutMovieFragment.ARG_OBJECT, movie);
            aboutMovieFragment.setArguments(args);
            return aboutMovieFragment;
        } else if (position == 1) {
            ReviewsFragment reviewsFragment = new ReviewsFragment();
            return reviewsFragment;
        } else if (position == 2) {
            CastFragment castFragment = new CastFragment();
            return castFragment;
        } else return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

}
