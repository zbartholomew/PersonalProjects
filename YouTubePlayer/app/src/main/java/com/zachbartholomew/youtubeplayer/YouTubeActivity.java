package com.zachbartholomew.youtubeplayer;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    static final String GOOGLE_API_KEY = "AIzaSyADhA6f8Dbg4LUBKmngt9KRyFpeYJnQ0Wk";
    static final String YOUTUBE_VIDEO_ID = "vNHpsC5ng_E";
    static final String YOUTUBE_PLAYLIST = "PLF206E906175C7E07";
    private static final String TAG = YouTubeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_youtube);
//        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.activity_youtube);

        // inflating root view to the layout, since this is the root view - null - is acceptable
        ConstraintLayout layout = (ConstraintLayout)
                getLayoutInflater().inflate(R.layout.activity_youtube, null);
        setContentView(layout);

        // example of adding a button to a view via code instead of xml
//        Button button1 = new Button(this);
//        button1.setLayoutParams(new ConstraintLayout.LayoutParams(300, 80));
//        button1.setText("Button added");
//        layout.addView(button1);

        YouTubePlayerView playerView = new YouTubePlayerView(this);
        playerView.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(playerView);
        playerView.initialize(GOOGLE_API_KEY, this);


    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer youTubePlayer, boolean wasRestored) {

        Log.d(TAG, "onInitializationSuccess: provider is " + provider.getClass().toString());

        Toast.makeText(this, "Initialized YouTube player successfully", Toast.LENGTH_LONG).show();

        youTubePlayer.setPlaybackEventListener(playbackEventListener);
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);

        if (!wasRestored) {
            youTubePlayer.loadVideo(YOUTUBE_VIDEO_ID);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult youTubeInitializationResult) {

        final int REQUEST_CODE = 1;

        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE).show();
        } else {
            String errorMessage = String.format(
                    "There was an error initializing the YouTubePlayer (%1$s)",
                    youTubeInitializationResult.toString());

            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener =
            new YouTubePlayer.PlaybackEventListener() {
                @Override
                public void onPlaying() {
                    Toast.makeText(
                            YouTubeActivity.this, "Good, video is playing ok", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPaused() {
                    Toast.makeText(
                            YouTubeActivity.this, "Video has paused", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStopped() {
                    Toast.makeText(
                            YouTubeActivity.this, "Video has stopped", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onBuffering(boolean b) {

                }

                @Override
                public void onSeekTo(int i) {

                }
            };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener =
            new YouTubePlayer.PlayerStateChangeListener() {
                @Override
                public void onLoading() {

                }

                @Override
                public void onLoaded(String s) {

                }

                @Override
                public void onAdStarted() {
                    Toast.makeText(
                            YouTubeActivity.this, "Click Ad now, make rich", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVideoStarted() {
                    Toast.makeText(
                            YouTubeActivity.this, "Video has Started", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onVideoEnded() {
                    Toast.makeText(
                            YouTubeActivity.this, "Video has completed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(YouTubePlayer.ErrorReason errorReason) {

                }
            };
}
