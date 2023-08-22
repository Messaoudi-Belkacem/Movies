package com.example.movies.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.movies.Movie;
import com.example.movies.MoviesRecyclerViewAdapter;
import com.example.movies.R;
import com.example.movies.activities.DetailActivity;
import com.example.movies.activities.VideoPlayerActivity;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView moviesRecyclerView;
    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    private ArrayList<Movie> movies = new ArrayList<>();

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        moviesRecyclerView = rootView.findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        Log.d("HomeFragment.java", "setting up moviesRecyclerViewAdapter");
        moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(movies, this);
        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);
        Log.d("HomeFragment.java", "moviesRecyclerViewAdapter set");

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

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        moviesRecyclerViewAdapter.notifyDataSetChanged();
        Log.d("HomeFragment.java", "notifyDataSetChanged method called");
    }
}