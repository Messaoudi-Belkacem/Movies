package com.example.movies.api;

import com.example.movies.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBService {

    @GET("search/movie")
    Call<MovieResponse> searchMoviesByName(@Query("api_key") String apiKey, @Query("query") String movieName);

}
