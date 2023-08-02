package com.example.movies.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Upsert;

import com.example.movies.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();
    @Upsert
    void upsert(Movie movies);
    @Delete
    void delete(Movie movie);
}
