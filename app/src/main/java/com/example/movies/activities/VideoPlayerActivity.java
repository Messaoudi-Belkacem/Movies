package com.example.movies.activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.media3.common.C;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.AspectRatioFrameLayout;
import androidx.media3.ui.PlayerView;
import com.example.movies.R;

    @UnstableApi public class VideoPlayerActivity extends AppCompatActivity {
    private PlayerView playerView;
    private ExoPlayer player;
    private String movieTitle;
    private TextView videoTitleTextView;
    private ImageView videoBackImageView;
    private ImageView scalingImageView;
    private ImageView playerImageView;
    private int i = 0;
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
        videoBackImageView = findViewById(R.id.videoBackButton);
        videoBackImageView.setOnClickListener(view -> {
            if (player != null) {
                player.release();
            }
            finish();
        });
        scalingImageView = findViewById(R.id.scaling);
        scalingImageView.setOnClickListener(view -> {
            if (i == 0) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
                i++;
            } else if (i == 1) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
                i++;
            } else if (i == 2) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
                i = 0;
            }
        });
        playerImageView = findViewById(R.id.playButton);
        playerImageView.setOnClickListener(view -> {
            if (player.getPlayWhenReady()) {
                player.setPlayWhenReady(false);
                playerImageView.setImageResource(R.drawable.ic_play);
            } else {
                player.setPlayWhenReady(true);
                playerImageView.setImageResource(R.drawable.ic_pause);
            }
        });
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
