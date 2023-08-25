package com.example.movies.activities;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private ImageView lockAndUnlockImageView;
    private ImageView rotateScreenImageView;
    private RelativeLayout root_layout;
    private int i = 0;
    private boolean locked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        setContentView(R.layout.activity_video_player);
        initializeUI();
        putBottomIconsOnTopOfNavBar();
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
                scalingImageView.setImageResource(R.drawable.ic_fullscreen);
                i++;
            } else if (i == 1) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
                scalingImageView.setImageResource(R.drawable.ic_zoom);
                i++;
            } else if (i == 2) {
                playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                player.setVideoScalingMode(C.VIDEO_SCALING_MODE_DEFAULT);
                scalingImageView.setImageResource(R.drawable.ic_fit_screen);
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
        lockAndUnlockImageView = findViewById(R.id.lockAndUnlockButton);
        root_layout = findViewById(R.id.root_layout);
        lockAndUnlockImageView.setOnClickListener(view -> {
            if (locked) {
                root_layout.setVisibility(View.VISIBLE);
                lockAndUnlockImageView.setImageResource(R.drawable.ic_unlock);
                locked = false;
            } else {
                root_layout.setVisibility(View.INVISIBLE);
                lockAndUnlockImageView.setImageResource(R.drawable.ic_lock);
                locked = true;
            }
        });
        rotateScreenImageView = findViewById(R.id.screenRotationButton);
        rotateScreenImageView.setOnClickListener(view -> {
            rotateScreen();
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

    private void putBottomIconsOnTopOfNavBar() {
        View rootLayout = findViewById(R.id.root_layout); // Replace with your root layout's ID
        View bottomIconsLayout = findViewById(R.id.bottomIconsLinearLayout);

        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            rootLayout.getWindowVisibleDisplayFrame(rect);

            int screenHeight = rootLayout.getHeight();
            int keyboardHeight = screenHeight - rect.bottom;

            if (keyboardHeight > screenHeight * 0.15) {
                // Keyboard is showing, adjust the position of your bottom icons
                bottomIconsLayout.setTranslationY(-keyboardHeight);
            } else {
                // Keyboard is not showing, reset the position of your bottom icons
                bottomIconsLayout.setTranslationY(0);
            }
        });
    }

    @SuppressLint("SourceLockedOrientationActivity")
    private void rotateScreen() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
}
