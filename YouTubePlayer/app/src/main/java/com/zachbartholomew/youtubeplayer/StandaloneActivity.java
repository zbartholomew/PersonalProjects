package com.zachbartholomew.youtubeplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeStandalonePlayer;

/**
 * Created by Zach on 6/22/2017.
 */

public class StandaloneActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standalone);

        Button buttonPlayVideo = (Button) findViewById(R.id.buttonPlayVideo);
        Button buttonPlayList = (Button) findViewById(R.id.buttonPlayPlaylist);

        buttonPlayList.setOnClickListener(this);
        buttonPlayVideo.setOnClickListener(this);

        // another way to implement an onclick listener
//        View.OnClickListener ourListener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        };
//        buttonPlayList.setOnClickListener(ourListener);

        // yet another way to set an onclick listener
//        buttonPlayList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.buttonPlayVideo:
                intent = YouTubeStandalonePlayer.createVideoIntent(this,
                        YouTubeActivity.GOOGLE_API_KEY, YouTubeActivity.YOUTUBE_VIDEO_ID,
                        0, true, false);
                break;

            case R.id.buttonPlayPlaylist:
                intent = YouTubeStandalonePlayer.createPlaylistIntent(this,
                        YouTubeActivity.GOOGLE_API_KEY, YouTubeActivity.YOUTUBE_PLAYLIST,
                        0, 0, true, true);
                break;
            default:

        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
