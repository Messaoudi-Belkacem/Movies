package com.example.movies.utilities;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;
import com.example.movies.R;

public class VideoPlayerActivity extends AppCompatActivity {
    private ExoPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_player);
        switchOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Hide the navigation bar and make it full screen
        hideNavigationBar();

        // Get the PlayerView from the layout
        PlayerView playerView = findViewById(R.id.playerView);

        // Create a SimpleExoPlayer instance
        player = new ExoPlayer.Builder(this).build();

        // Attach the player to the PlayerView
        playerView.setPlayer(player);

        // Get the video path from the Intent's extra data
        String videoPath = getIntent().getStringExtra("VIDEO_PATH");

        // Set the media URI to play the video (replace "video_path" with your video's local or remote path)
        Uri uri = Uri.parse(videoPath);

        // Build the media item.
        MediaItem mediaItem = MediaItem.fromUri(uri);

        // Prepare the player with the media source
        player.setMediaItem(mediaItem);

        // Prepare the player asynchronously
        player.prepare();

        // Start the video playback
        player.play();
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
        switchOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void switchOrientation(int desiredOrientation) {
        // Get the current orientation
        int currentOrientation = getResources().getConfiguration().orientation;

        // Check the current orientation and set the new orientation accordingly
        if (currentOrientation != desiredOrientation) {
            setRequestedOrientation(desiredOrientation);
        }
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
