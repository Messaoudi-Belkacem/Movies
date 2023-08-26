package com.example.movies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.media3.common.util.UnstableApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.movies.Movie;
import com.example.movies.MoviesRecyclerViewAdapter;
import com.example.movies.R;
import com.example.movies.activities.DetailActivity;
import com.example.movies.activities.VideoPlayerActivity;
import com.example.movies.room.AppDatabase;
import com.example.movies.viewmodels.MainViewModel;
import com.example.movies.viewmodels.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private final String tag = "HomeFragment.java";
    private MainViewModel mainViewModel;
    private RecyclerView moviesRecyclerView;
    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    private ArrayList<Movie> movies = new ArrayList<>();
    // Database
    private AppDatabase appDatabase;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        appDatabase = Room.databaseBuilder(
                        requireContext(),
                        AppDatabase.class,
                        "movie"
                        ).build();

        mainViewModel = new ViewModelProvider(this, new ViewModelFactory(appDatabase.movieDao())).get(MainViewModel.class);

        moviesRecyclerView = rootView.findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(movies, this);
        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);

        mainViewModel.getMoviesData().observe(getViewLifecycleOwner(), (Observer<List<Movie>>) movies -> {
            Log.d(tag, "Observed List Changed !");
            this.movies = (ArrayList<Movie>) movies;
            moviesRecyclerViewAdapter.notifyDataSetChanged();
        });

        return rootView;
    }

    public static void startVideoPlayerActivity(String videoPath, String videoTitle, Context context) {
        // Create an Intent to start VideoPlayerActivity
        Intent intent = new Intent(context, VideoPlayerActivity.class);
        intent.putExtra("VIDEO_PATH", videoPath);
        intent.putExtra("VIDEO_TITLE", videoTitle);
        context.startActivity(intent);
    }

    public void startDetailActivity(Movie movie) {
        // Create an Intent to start VideoPlayerActivity
        Intent intent = new Intent(requireContext(), DetailActivity.class);
        intent.putExtra("MOVIE", movie);
        requireContext().startActivity(intent);
    }
}