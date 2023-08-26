package com.example.movies.viewmodels;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.movies.Movie;
import com.example.movies.MovieResponse;
import com.example.movies.api.TMDBApiClient;
import com.example.movies.api.TMDBService;
import com.example.movies.room.MovieDao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;

public class MainViewModel extends ViewModel {
    private final MovieDao movieDao;
    private final MutableLiveData<ArrayList<Movie>> moviesData = new MutableLiveData<>();
    private final ArrayList<Movie> moviesOnDevice = new ArrayList<>();
    private final String tag = "MainViewModel";

    public MainViewModel(MovieDao movieDao) {
        this.movieDao = movieDao;
        Executors.newSingleThreadExecutor().execute(() -> {
            moviesData.postValue((ArrayList<Movie>) movieDao.getAllMovies());
            // Add a callback to run after postValue is complete
        });
        moviesData.observeForever(new Observer<>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                // This callback is executed when postValue is complete
                accessMoviesFolder();
                cleanNonExistingMovies();

                // Remove the observer to avoid memory leaks
                moviesData.removeObserver(this);
            }
        });
    }

    public MutableLiveData<ArrayList<Movie>> getMoviesData() {
        return moviesData;
    }

    private void setMoviesDataFromDb() {
        Executors.newSingleThreadExecutor().execute(() -> {
            moviesData.setValue((ArrayList<Movie>) movieDao.getAllMovies());
            Log.d(tag, "moviesData set from movies data base " + moviesData.getValue().size());
        });
    }

    // Database operations
    public void upsertMovie(Movie movie) {
        // Perform database insert on a background thread
        Executors.newSingleThreadExecutor().execute(() -> movieDao.upsert(movie));
    }

    public void accessMoviesFolder() {
        String folderPath = Environment.getExternalStorageDirectory() + "/Movies";
        Log.d(tag, "Permission : " + Environment.isExternalStorageManager());
        File folder = new File(folderPath);
        ArrayList<Movie> movies;
        movies = moviesData.getValue();
        if (folder.exists() && folder.isDirectory()) {
            Log.d(tag, "Movie folder found !");
            File[] movieFiles = folder.listFiles();
            if (!containsMovies(movieFiles)) {
                // Movies Folder is empty
                Log.d(tag, "Movie folder does not contain movies !");
            } else {
                // Movies Folder is not empty
                // Process the movie files
                for (File movieFile : movieFiles) {
                    String movieTitle = movieFile.getName();
                    if (movieTitle.endsWith(".mp4") || movieTitle.endsWith(".mkv")) {
                        if(movieTitle.endsWith(".mp4")) {
                            movieTitle = movieTitle.substring(0, movieTitle.indexOf(".mp4"));
                        } else if (movieTitle.endsWith(".mkv")) {
                            movieTitle = movieTitle.substring(0, movieTitle.indexOf(".mkv"));
                        }
                        String movieFilePath = movieFile.getAbsolutePath();
                        Movie movie = new Movie(movieTitle, movieFilePath);
                        moviesOnDevice.add(movie);
                        if (!movies.contains(movie)) {
                            // If the movie is not in the database
                            Log.d(tag,  movieTitle +" movie does not exist in the database");
                            performMovieSearch(movieTitle, movieFilePath);
                        } else {
                            Log.d(tag,  movie.getTitle() + " movie already exists in the database!");
                        }
                    }
                }
                // notify that all movies have been added successfully
                Log.d(tag, "All movies have been added successfully");
            }
        } else {
            // Handle the scenario where the movies folder does not exist or is not a directory
            Log.d(tag, "Movie folder not found !");
        }
    }

    private void performMovieSearch(String movieTitle, String movieFilePath) {
        Log.d(tag, "search being performed on movie " + movieTitle);
        Executors.newSingleThreadExecutor().execute(() -> {
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
                        upsertMovie(movie);
                        setMoviesDataFromDb();
                        Log.d(tag,  movie.getTitle() + " added successfully !");
                    } else {
                        // TODO
                        // Handle error
                        Log.d(tag,  "Response is unsuccessful !");
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                    // Handle failure
                    if (t instanceof IOException) {
                        // Network or conversion error (e.g., no internet connection)
                        // Handle the IOException appropriately
                        Log.d(tag, "failure, no internet connection !");
                    } else if (t instanceof HttpException) {
                        HttpException httpException = (HttpException) t;
                        // HTTP error response (e.g., 404, 500)
                        Log.d(tag, "Http error " + httpException.code());
                    } else {
                        // Other unknown errors
                        Log.d(tag, "Unknown error");
                        t.printStackTrace();
                    }
                }
            });
        });
    }

    private boolean containsMovies(File[] files) {
        if (files != null) {
            for (File file : files) {
                if (file.getName().endsWith(".mp4")) return true;
            }
        }
        return false;
    }

    private void cleanNonExistingMovies() {
        Log.d(tag, "cleanNonExistingMovies method called !");
        ArrayList<Movie> movieArrayList = getMoviesData().getValue();
        if (movieArrayList != null) {
            for (int i = 0; i < movieArrayList.size(); i++) {
                if (!moviesOnDevice.contains(movieArrayList.get(i))) {
                    Movie movieToDelete = movieArrayList.get(i);
                    Executors.newSingleThreadExecutor().execute(() -> movieDao.delete(movieToDelete));
                    Log.d(tag, movieToDelete.title + " has been deleted successfully");
                }
            }
        } else Log.d(tag, "movieArraylist is null");
    }
}
