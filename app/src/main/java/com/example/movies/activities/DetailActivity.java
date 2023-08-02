package com.example.movies.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.movies.CollectionAdapter;
import com.example.movies.Movie;
import com.example.movies.R;
import com.example.movies.fragments.AboutMovieFragment;
import com.example.movies.fragments.CastFragment;
import com.example.movies.fragments.HomeFragment;
import com.example.movies.fragments.ReviewsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    MaterialToolbar topAppBar;
    ImageView backDropImageView;
    ImageView posterImageView;
    TextView titleTextView;
    TextView yearTextView;
    TextView durationTextView;
    TextView genreTextView;
    Button watchButton;
    TabLayout tabLayout;
    AboutMovieFragment aboutMovieFragment;
    ReviewsFragment reviewsFragment;
    CastFragment castFragment;
    Movie movie;
    CollectionAdapter collectionAdapter;
    ViewPager2 viewPager2;

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

        yearTextView = findViewById(R.id.yearTextView);
        Date releaseDate = movie.getRelease_date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(releaseDate);
        yearTextView.setText(String.valueOf(calendar.get(Calendar.YEAR)));

        durationTextView = findViewById(R.id.durationTextView);
        durationTextView.setText("unknown");

        genreTextView = findViewById(R.id.genreTextView);
        genreTextView.setText(movie.getGenre());

        watchButton = findViewById(R.id.watchButton);
        watchButton.setOnClickListener(view -> {
            HomeFragment.startVideoPlayerActivity(movie.getAbsolutePath(), this);
        });

        tabLayout = findViewById(R.id.tabLayout);

        collectionAdapter = new CollectionAdapter(this);
        collectionAdapter.setMovie(movie);
        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setAdapter(collectionAdapter);

        new  TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("About Movie");
                    break;
                case 1:
                    tab.setText("Reviews");
                    break;
                case 2:
                    tab.setText("Cast");
                    break;
            }
        })).attach();
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
