package com.example.android_firebase_todo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

public class ShowCreditsActivity extends AppCompatActivity {

    private static final String VIDEO_NAME = "app_credits";
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_credits);

        mVideoView = findViewById(R.id.creditsVideoView);
    }

    private Uri getVideo() {
        return Uri.parse("android.resource://" + getPackageName() + "/raw/" + ShowCreditsActivity.VIDEO_NAME);
    }

    private void initPlayer() {
        mVideoView.setVideoURI(getVideo());
        mVideoView.setOnCompletionListener(mp -> {
            releasePlayer();
            finish();
        });
        mVideoView.start();
    }

    private void releasePlayer() {
        mVideoView.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        releasePlayer();
    }
}