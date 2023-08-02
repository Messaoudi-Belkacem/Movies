package com.example.movies.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.movies.Movie;
import com.example.movies.utilities.DateConverter;

@Database(entities = {Movie.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
