package com.example.memorybox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DisplayFullImageActivity extends AppCompatActivity {
    ImageView image;
    Toolbar toolbar_photo;
    BottomNavigationView bottomNavigationView_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_image);
        image = findViewById(R.id.imageOther);
        toolbar_photo = findViewById(R.id.toolbar_photo);
        bottomNavigationView_photo = findViewById(R.id.navigation_bar_photo);
        setSupportActionBar(toolbar_photo);

        String pathImage = getIntent().getStringExtra("path_image");
        Glide.with(this).load(pathImage).asBitmap().into(image);

        toolbar_photo.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_photo_setAs:
                        WallpaperManager myWallpaperManager
                                = WallpaperManager.getInstance(getApplicationContext());
                        try {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            myWallpaperManager.setBitmap(bitmap);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        return true;
                    default:
                        return true;
                }
            }
        });

        bottomNavigationView_photo.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.photo_navigation_share:
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        shareImage(bitmap);
                        return true;
                    default:
                        return true;
                }
            }

            private void shareImage(Bitmap bitmap) {
                Uri uri = getImageToShare(bitmap);
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.putExtra(Intent.EXTRA_STREAM, uri);

                intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image");

                intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here");

                intent.setType("image/png");

                startActivity(Intent.createChooser(intent, "Share Via"));
            }

            private Uri getImageToShare(Bitmap bitmap) {
                File imagefolder = new File(getCacheDir(), "images");
                Uri uri = null;
                try {
                    imagefolder.mkdirs();
                    File file = new File(imagefolder, "shared_image.png");
                    FileOutputStream outputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
                    outputStream.flush();
                    outputStream.close();
                    uri = FileProvider.getUriForFile(DisplayFullImageActivity.this, "com.example.memorybox", file);
                } catch (Exception e) {
//                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                return uri;

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photo_option_menu, menu);
        return true;
    }
}