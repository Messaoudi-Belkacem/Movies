package com.example.movies;

import java.util.Date;

public class Movie {

    private int id;
    private String overview;
    private String title;
    private float vote_average;
    private String genre;
    private Date release_date;
    private String absolutePath;
    private String poster_path;

    public Movie(String title,String absolutePath) {
        this.title = title;
        this.absolutePath = absolutePath;
    }

    public Movie(int id, String overview, String title, float vote_average, String genre, Date release_date, String absolutePath, String poster_path) {
        this.id = id;
        this.overview = overview;
        this.title = title;
        this.vote_average = vote_average;
        this.genre = genre;
        this.release_date = release_date;
        this.absolutePath = absolutePath;
        this.poster_path = poster_path;

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
}
