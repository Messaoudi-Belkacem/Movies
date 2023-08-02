package com.example.movies.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = MovieDao.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
}
