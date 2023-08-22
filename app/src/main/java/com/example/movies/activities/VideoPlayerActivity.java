package com.example.movies.activities;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.example.movies.R;

public class VideoPlayerActivity extends AppCompatActivity {
    PlayerView playerView;
    private ExoPlayer player;
    private String movieTitle;
    private TextView videoTitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_video_player);
        initializeUI();
        playMovie();
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the player resources when the activity is destroyed
        player.release();
    }

    private void switchOrientation(int desiredOrientation) {
        // Get the current orientation
        int currentOrientation = getResources().getConfiguration().orientation;

        // Check the current orientation and set the new orientation accordingly
        if (currentOrientation != desiredOrientation) {
            setRequestedOrientation(desiredOrientation);
        }
    }

    private void initializeUI() {
        playerView = findViewById(R.id.playerView);
        movieTitle = getIntent().getStringExtra("VIDEO_TITLE");
        videoTitleTextView = findViewById(R.id.videoTitleTextView);
        videoTitleTextView.setText(movieTitle);
    }

    private void playMovie() {
        playerView.setKeepScreenOn(true);
        player = new ExoPlayer.Builder(this).build();
        playerView.setPlayer(player);
        String videoPath = getIntent().getStringExtra("VIDEO_PATH");
        Uri uri = Uri.parse(videoPath);
        MediaItem mediaItem = MediaItem.fromUri(uri);
        player.setMediaItem(mediaItem);
        player.prepare();
        player.play();
    }

    private void setFullScreen() {
        WindowInsetsControllerCompat windowInsetsController = WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }
}
