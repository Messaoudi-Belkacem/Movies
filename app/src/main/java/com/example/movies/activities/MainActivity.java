package com.example.movies.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.Movie;
import com.example.movies.MovieResponse;
import com.example.movies.R;
import com.example.movies.api.TMDBApiClient;
import com.example.movies.api.TMDBService;
import com.example.movies.fragments.HomeFragment;
import com.example.movies.fragments.SearchFragment;
import com.example.movies.fragments.WatchListFragment;
import com.example.movies.utilities.Permissions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    SearchFragment searchFragment;

    WatchListFragment watchListFragment;
    private ArrayList<Movie> movies = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Permissions.checkIfPermissionIsGranted(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            accessMoviesFolder();
        }

        homeFragment = new HomeFragment();
        homeFragment.setMovies(movies);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
        searchFragment = new SearchFragment();
        watchListFragment = new WatchListFragment();

        initializeUI();
    }

    private void initializeUI() {
        BottomNavigationView bottomNavigationBar = findViewById(R.id.bottom_navigation);
        bottomNavigationBar.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.item_2) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, searchFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.item_3) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, watchListFragment).commit();
                return true;
            } else return false;
        });
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
                    performMovieSearch(movieTitle, movieFilePath);
                }
            }
        } else {
            // TODO
            // Handle the scenario where the movies folder does not exist or is not a directory
        }
        Log.d("HomeFragment.java", "accessMoviesFolder method ended");
    }

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
}