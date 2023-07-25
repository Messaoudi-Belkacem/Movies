package com.example.movies.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TMDBApiClient {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String API_KEY = "4bd6f93d8ac6a8d6b7c22202100f6e03";

    private static TMDBService tmdbService;

    public static TMDBService getTMDBService() {
        if (tmdbService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            tmdbService = retrofit.create(TMDBService.class);
        }
        return tmdbService;
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
