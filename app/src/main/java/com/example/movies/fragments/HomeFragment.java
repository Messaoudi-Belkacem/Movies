package com.example.movies.fragments;

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
import com.example.movies.utilities.VideoPlayerActivity;
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
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        /**
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            accessMoviesFolder();
        }
         */

        Log.d("HomeFragment.java", "setting up moviesRecyclerViewAdapter");
        moviesRecyclerViewAdapter = new MoviesRecyclerViewAdapter(movies, this);
        moviesRecyclerView.setAdapter(moviesRecyclerViewAdapter);
        Log.d("HomeFragment.java", "moviesRecyclerViewAdapter set");

        return rootView;
    }

    /**
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
                    performMovieSearch(movieTitle, movieFilePath);
                }
            }
        } else {
            // TODO
            // Handle the scenario where the movies folder does not exist or is not a directory
        }
        Log.d("HomeFragment.java", "accessMoviesFolder method ended");
    }
    */

    public void startVideoPlayerActivity(String videoPath) {
        // Create an Intent to start VideoPlayerActivity
        Intent intent = new Intent(requireContext(), VideoPlayerActivity.class);
        intent.putExtra("VIDEO_PATH", videoPath);
        requireContext().startActivity(intent);
    }

    /**
    private void performMovieSearch(String movieTitle, String movieFilePath) {

        TMDBService service = TMDBApiClient.getTMDBService();
        Call<MovieResponse> call = service.searchMoviesByName(TMDBApiClient.getApiKey(), movieTitle);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    List<Movie> results = movieResponse.getResults();
                    // Process the list of movies here
                    Movie movie = results.get(0);
                    movie.setAbsolutePath(movieFilePath);
                    movies.add(movie);
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Handle failure
            }
        });
    }
     */

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }
}