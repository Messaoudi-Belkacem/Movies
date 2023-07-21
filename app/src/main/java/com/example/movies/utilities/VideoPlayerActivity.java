package com.example.movies.utilities;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.movies.R;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {
    private SimpleExoPlayer player;
    private PlayerView playerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        // Get the PlayerView from the layout
        playerView = findViewById(R.id.playerView);

        // Create a SimpleExoPlayer instance
        player = new SimpleExoPlayer.Builder(this).build();

        // Attach the player to the PlayerView
        playerView.setPlayer(player);

        // Get the video path from the Intent's extra data
        String videoPath = getIntent().getStringExtra("video_path");

        // Set the media URI to play the video (replace "video_path" with your video's local or remote path)
        Uri uri = Uri.parse(videoPath);

        // Create a media source
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Movies"));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(uri));

        // Prepare the player with the media source
        player.setMediaSource(mediaSource);

        // Prepare the player asynchronously
        player.prepare();

        // Start the video playback
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the player resources when the activity is destroyed
        player.release();
    }
}
