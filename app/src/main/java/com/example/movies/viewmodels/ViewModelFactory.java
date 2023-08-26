package com.example.movies.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.movies.room.MovieDao;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final MovieDao movieDao; // Pass any dependencies your ViewModels need

    public ViewModelFactory(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(movieDao);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
