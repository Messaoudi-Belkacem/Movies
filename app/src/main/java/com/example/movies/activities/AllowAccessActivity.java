package com.example.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movies.R;
import com.example.movies.utilities.Permissions;

public class AllowAccessActivity extends AppCompatActivity {
    private final String tag = "AllowAccessActivity";
    private Button allowAccessButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allow_access);
        initializeUI();
    }

    private void initializeUI() {
        allowAccessButton = findViewById(R.id.allowAccessButton);
        allowAccessButton.setOnClickListener(view -> {
            Permissions.requestStoragePermission(this);
            if (Permissions.checkIfPermissionIsGranted(this)) {
                Intent intent = new Intent(AllowAccessActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the splash screen activity
            } else Log.d(tag, "access is denied");
        });
    }
}
