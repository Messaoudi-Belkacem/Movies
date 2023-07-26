package com.example.movies.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movies.Movie;
import com.example.movies.MovieResponse;
import com.example.movies.R;
import com.example.movies.api.TMDBApiClient;
import com.example.movies.api.TMDBService;
import com.example.movies.fragments.HomeFragment;
import com.example.movies.fragments.SearchFragment;
import com.example.movies.fragments.WatchListFragment;
import com.example.movies.utilities.DialogUtilities;
import com.example.movies.utilities.Permissions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
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

        accessMoviesFolder();
        Log.d("MainActivity.java", "accessMoviesFolder Called!");

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
        searchFragment = new SearchFragment();
        watchListFragment = new WatchListFragment();

        initializeUI();
        Log.d("MainActivity", "onCreate method called");

    }

    private void initializeUI() {

        BottomNavigationView bottomNavigationBar = findViewById(R.id.bottom_navigation);
        bottomNavigationBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
                homeFragment.setMovies(movies);
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

    private void accessMoviesFolder() {

        String folderPath = Environment.getExternalStorageDirectory() + "/Movies";
        Log.d("Permission", String.valueOf(Environment.isExternalStorageManager()));
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {

            Log.d("MainActivity.java", "Movie folder found !");
            File[] movieFiles = folder.listFiles();

            if (!containsMovies(movieFiles)) {
                // Movies Folder is empty
                Log.d("MainActivity.java", "Movie folder is empty !");
                AlertDialog dialog = DialogUtilities.MoviesFolderIsEmptyDialog(this);
                dialog.show();
                Toast.makeText(this, "Movies folder is empty !", Toast.LENGTH_SHORT).show();
            } else {
                // Process the movie files as needed
                for (File movieFile : movieFiles) {
                    // Retrieve movie information and store in your app's data model
                    String movieTitle = movieFile.getName();
                    String movieFilePath = movieFile.getAbsolutePath();

                    if (movieTitle.endsWith(".mp4")) {
                        movieTitle = movieTitle.substring(0, movieTitle.indexOf(".mp4"));
                        performMovieSearch(movieTitle, movieFilePath);
                    }
                }
                // notify that all movies have been added successfully
                Log.d("MainActivity.java", "All movies have been added successfully");
            }
        } else {
            // Handle the scenario where the movies folder does not exist or is not a directory
            Log.d("MainActivity.java", "Movie folder not found !");
            AlertDialog dialog = DialogUtilities.MoviesFolderDialog(this);
            dialog.show();
            Toast.makeText(this, "Movies folder not found !", Toast.LENGTH_SHORT).show();
        }
    }

    private void performMovieSearch(String movieTitle, String movieFilePath) {

        TMDBService service = TMDBApiClient.getTMDBService();
        Call<MovieResponse> call = service.searchMoviesByName(TMDBApiClient.getApiKey(), movieTitle);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    MovieResponse movieResponse = response.body();
                    List<Movie> results = movieResponse.getResults();
                    // Process the list of movies here
                    Movie movie = results.get(0);
                    movie.setAbsolutePath(movieFilePath);
                    movies.add(movie);
                    Log.d("MainActivity.java",  movie.getTitle() + " added successfully !");
                    homeFragment.setMovies(movies);
                } else {
                    // TODO
                    // Handle error
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                // Handle failure
                if (t instanceof IOException) {
                    // Network or conversion error (e.g., no internet connection)
                    // Handle the IOException appropriately
                    Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity.java", "failure, no internet connection !");
                } else if (t instanceof HttpException) {
                    HttpException httpException = (HttpException) t;
                    // HTTP error response (e.g., 404, 500)
                    Toast.makeText(getApplicationContext(), "Http error " + httpException.code(), Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity.java", "Http error " + httpException.code());
                } else {
                    // Other unknown errors
                    Toast.makeText(getApplicationContext(), "Unknown error", Toast.LENGTH_SHORT).show();
                    Log.d("MainActivity.java", "Unknown error");
                }
            }
        });
    }

    private boolean containsMovies(File[] files) {
        for (File file : files) {
            if (file.getName().endsWith(".mp4")) return true;
        }
        return false;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

}