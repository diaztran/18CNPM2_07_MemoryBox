package com.example.memorybox;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;

import hb.xvideoplayer.MxVideoPlayer;
import hb.xvideoplayer.MxVideoPlayerWidget;

public class PlayVideoActivity extends AppCompatActivity {

    Toolbar toolbar_video;
    BottomNavigationView bottomNavigationView_video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        String pathVideo=getIntent().getStringExtra("path_video");

        MxVideoPlayerWidget videoPlayerWidget = findViewById(R.id.mpw_video_player);
        videoPlayerWidget.startPlay(pathVideo, MxVideoPlayer.SCREEN_LAYOUT_NORMAL,"gaga");
        toolbar_video = findViewById(R.id.toolbar_video);
        bottomNavigationView_video = findViewById(R.id.navigation_bar_video);
        setSupportActionBar(toolbar_video);

        // Handle events
        bottomNavigationView_video.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.video_navigation_info:
                        FragmentManager fm = getSupportFragmentManager();
                        VideoInformationFragment videoInformationFragment = VideoInformationFragment.newInstance("Video Information", "Dialog");
                        videoInformationFragment.show(fm, null);
                        return true;
                    case R.id.video_navigation_share:
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("video/*");

                        share.putExtra(Intent.EXTRA_SUBJECT, "Video");
                        share.putExtra(Intent.EXTRA_TITLE, "Sharing Video");

                        File imageFileToShare = new File(Environment.getExternalStorageDirectory() + pathVideo);

                        Uri uri = FileProvider.getUriForFile(PlayVideoActivity.this, "com.example.memorybox", imageFileToShare);

                        share.putExtra(Intent.EXTRA_STREAM, uri);

                        share.setPackage("com.abc.in");
                        startActivity(Intent.createChooser(share, "Message"));
                    default:
                        return false;
                }
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        MxVideoPlayer.releaseAllVideos();
    }
    @Override
    public void onBackPressed() {
        if (MxVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video_option_menu, menu);
        return true;
    }
}