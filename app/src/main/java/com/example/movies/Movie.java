package com.example.movies;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "movies")
public class Movie implements Serializable {

    @PrimaryKey
    public int id;
    public String overview;
    public String title;
    public float vote_average;
    public String genre;
    public Date release_date;
    public String absolutePath;
    public String poster_path;
    public String backdrop_path;

    @Ignore
    public Movie(String title,String absolutePath) {
        this.title = title;
        this.absolutePath = absolutePath;
    }

    public Movie(int id, String overview, String title, float vote_average, String genre, Date release_date, String absolutePath, String poster_path, String backdrop_path) {
        this.id = id;
        this.overview = overview;
        this.title = title;
        this.vote_average = vote_average;
        this.genre = genre;
        this.release_date = release_date;
        this.absolutePath = absolutePath;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }
    public float getVote_average() {
        return vote_average;
    }

    public String getGenre() {
        return genre;
    }
    public Date getRelease_date() {
        return release_date;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null) {
            if (obj.getClass() == Movie.class) {
                return (this.absolutePath.equals(((Movie) obj).absolutePath));
            }
        }
        return false;
    }
}
