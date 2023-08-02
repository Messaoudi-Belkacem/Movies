package com.example.movies;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Movie implements Serializable {

    @PrimaryKey
    private int id;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "title")
    private String title;
    @ColumnInfo(name = "vote_average")
    private float vote_average;
    @ColumnInfo(name = "genre")
    private String genre;
    @ColumnInfo(name = "release_date")
    private Date release_date;
    @ColumnInfo(name = "absolutePath")
    private String absolutePath;
    @ColumnInfo(name = "poster_path")
    private String poster_path;
    @ColumnInfo(name = "backdrop_path")
    private String backdrop_path;

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

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
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

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }
}
