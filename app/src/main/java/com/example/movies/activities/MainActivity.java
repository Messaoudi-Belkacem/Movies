package com.example.movies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.movies.fragments.HomeFragment;
import com.example.movies.R;
import com.example.movies.fragments.SearchFragment;
import com.example.movies.fragments.WatchListFragment;
import com.example.movies.utilities.Permissions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    HomeFragment homeFragment;
    SearchFragment searchFragment;

    WatchListFragment watchListFragment;
    private BottomNavigationView bottomNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Permissions.checkIfPermissionIsGranted(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
        searchFragment = new SearchFragment();
        watchListFragment = new WatchListFragment();
        initializeUI();
    }
    private void initializeUI() {
        bottomNavigationBar = findViewById(R.id.bottom_navigation);
        bottomNavigationBar.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.item_1) {
                Toast.makeText(this,"Home selected",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, homeFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.item_2) {
                Toast.makeText(this,"Search selected",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, searchFragment).commit();
                return true;
            } else if (item.getItemId() == R.id.item_3) {
                Toast.makeText(this,"Watch list selected",Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, watchListFragment).commit();
                return true;
            } else return false;
        });
    }
}