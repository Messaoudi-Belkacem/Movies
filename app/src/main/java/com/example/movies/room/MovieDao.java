package com.example.movies.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.movies.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();
    @Insert
    void insertAllMovies(Movie... movies);
    @Delete
    void delete(Movie movie);
}
