package com.example.movies.utilities;

import android.net.Uri;
import android.os.Bundle;

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
    protected void onDestroy() {
        super.onDestroy();
        // Release the player resources when the activity is destroyed
        player.release();
    }
}
