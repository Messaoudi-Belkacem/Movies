package com.example.movies.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.movies.Movie;
import com.example.movies.MovieResponse;
import com.example.movies.R;
import com.example.movies.api.TMDBApiClient;
import com.example.movies.api.TMDBService;
import com.example.movies.fragments.HomeFragment;
import com.example.movies.fragments.SearchFragment;
import com.example.movies.fragments.WatchListFragment;
import com.example.movies.room.AppDatabase;
import com.example.movies.room.MovieDao;
import com.example.movies.utilities.DialogUtilities;
import com.example.movies.utilities.Permissions;
import com.example.movies.viewmodels.MainViewModel;
import com.example.movies.viewmodels.ViewModelFactory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    // Fragments
    private HomeFragment homeFragment;
    private SearchFragment searchFragment;
    private WatchListFragment watchListFragment;
    // Database
    private AppDatabase appDatabase;
    private ArrayList<Movie> moviesDb = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                        AppDatabase.class,
                        "movie")
                        .build();

        mainViewModel = new ViewModelProvider(this, new ViewModelFactory(appDatabase.movieDao()))
                .get(MainViewModel.class);

        Log.d("MainActivity.java", "setMoviesDb Called!");

        homeFragment = new HomeFragment();
        searchFragment = new SearchFragment();
        watchListFragment = new WatchListFragment();

        mainViewModel.getMoviesData().observe(this, (Observer<List<Movie>>) movies -> {
            moviesDb = (ArrayList<Movie>) movies;
            homeFragment.setMovies(moviesDb);
        });

        initializeUI();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();

        Log.d("MainActivity", "onCreate method called");
    }

    private void initializeUI() {
        BottomNavigationView bottomNavigationBar = findViewById(R.id.bottom_navigation);
        bottomNavigationBar.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_1) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
                homeFragment.setMovies(moviesDb);
                Log.d("MainActivity.java", "home fragment set !");
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
}