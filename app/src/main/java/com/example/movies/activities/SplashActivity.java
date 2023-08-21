package com.example.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.R;
import com.example.movies.utilities.Permissions;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 2000; // Splash screen delay in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for the splash screen activity
        setContentView(R.layout.activity_splash);

        // Delay for a few seconds and then start the main activity
        new Handler().postDelayed(() -> {
            if (Permissions.checkIfPermissionIsGranted(this)) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the splash screen activity
            } else {
                Intent intent = new Intent(SplashActivity.this, AllowAccessActivity.class);
                startActivity(intent);
                finish(); // Close the splash screen activity
            }
        }, SPLASH_DELAY);
    }
}
