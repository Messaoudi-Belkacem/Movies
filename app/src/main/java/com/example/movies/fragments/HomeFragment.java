package com.example.movies.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Movie;
import com.example.movies.MoviesRecyclerViewAdapter;
import com.example.movies.R;

import java.io.File;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private RecyclerView moviesRecyclerView;
    private MoviesRecyclerViewAdapter moviesRecyclerViewAdapter;
    private ArrayList<Movie> movies = new ArrayList<>();

    public HomeFragment() {
        // empty constructor required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        moviesRecyclerView = rootView.findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            accessMoviesFolder();
        }

        moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(movies, this);
        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);

        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    private void accessMoviesFolder() {

        String folderPath = Environment.getExternalStorageDirectory() + "/Movies";
        Log.d("Permission", String.valueOf(Environment.isExternalStorageManager()));
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] movieFiles = folder.listFiles();

            // Process the movie files as needed
            for (File movieFile : movieFiles) {
                // Retrieve movie information and store in your app's data model
                String movieTitle = movieFile.getName();
                String movieFilePath = movieFile.getAbsolutePath();
                // TODO


                if (movieTitle.endsWith(".mp4")) {
                    movieTitle = movieTitle.substring(0, movieTitle.indexOf(".mp4"));
                    movies.add(new Movie(movieTitle, movieFilePath));
                }
            }
        } else {
            // TODO
            // Handle the scenario where the movies folder does not exist or is not a directory
        }
    }

    public void startVideoPlayerActivity(String videoPath) {
        Intent playVideoIntent = new Intent(Intent.ACTION_VIEW);
        playVideoIntent.setDataAndType(Uri.parse(videoPath), "video/*");
        startActivity(playVideoIntent);
    }


}