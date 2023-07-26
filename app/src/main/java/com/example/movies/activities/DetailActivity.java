package com.example.movies.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.Movie;
import com.example.movies.R;
import com.example.movies.fragments.HomeFragment;
import com.google.android.material.appbar.MaterialToolbar;

public class DetailActivity extends AppCompatActivity {

    MaterialToolbar topAppBar;
    ImageView backDropImageView;
    ImageView posterImageView;

    TextView titleTextView;
    TextView aboutMovieTextView;
    Button watchButton;
    Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie = (Movie) getIntent().getSerializableExtra("MOVIE");

        initializeUI();

        Log.d("DetailActivity.java", "DetailActivity launched successfully");
    }

    private void initializeUI() {
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(view -> {
            finish();
        });
        backDropImageView = findViewById(R.id.backDropImageView);
        setBackDropImageView();
        posterImageView = findViewById(R.id.posterImageView);
        setPosterImageView();
        titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(movie.getTitle());
        aboutMovieTextView = findViewById(R.id.aboutMovieTextView);
        aboutMovieTextView.setText(movie.getOverview());
        watchButton = findViewById(R.id.watchButton);
        watchButton.setOnClickListener(view -> {
            HomeFragment.startVideoPlayerActivity(movie.getAbsolutePath(), this);
        });
    }

    private void setBackDropImageView() {
        // here you put the image of every movie to the recycler view
        String imageUrl = "https://image.tmdb.org/t/p/original/" + movie.getBackdrop_path();

        // Load the image using Glide
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.media) // Placeholder image while loading
                .error(R.drawable.media) // Image to display if loading fails
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); // Cache the image automatically

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(backDropImageView);
    }

    private void setPosterImageView() {
        // here you put the image of every movie to the recycler view
        String imageUrl = "https://image.tmdb.org/t/p/original/" + movie.getPoster_path();

        // Load the image using Glide
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.media) // Placeholder image while loading
                .error(R.drawable.media) // Image to display if loading fails
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC); // Cache the image automatically

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(posterImageView);
    }

}
