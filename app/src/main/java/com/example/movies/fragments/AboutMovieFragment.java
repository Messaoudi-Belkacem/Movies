package com.example.movies.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.movies.Movie;
import com.example.movies.R;

public class AboutMovieFragment extends Fragment {

    public static final String ARG_OBJECT = "object";
    private TextView overviewTextView;
    Movie movie;

    public AboutMovieFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragement_about_movie, container, false);

        overviewTextView = rootView.findViewById(R.id.overviewTextView);

        Log.d("AboutMovieFragment.java", "The onCreateView method called");

        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        movie = (Movie) args.getSerializable(ARG_OBJECT);
        overviewTextView.setText(movie.getOverview());
    }

    public void setOverviewTextView(String s) {
        overviewTextView.setText(s);
        Log.d("AboutMovieFragment.java", "The setOverviewTextView method called");
    }

}
