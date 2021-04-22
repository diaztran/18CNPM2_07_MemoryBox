package com.example.memorybox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayFullImageActivity extends AppCompatActivity {
    ImageView image;
    Toolbar toolbar_photo;
    BottomNavigationView bottomNavigationView_photo;
    private String pathImage;
    private ArrayList<String> listInfoPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_image);
        image = findViewById(R.id.imageOther);
        toolbar_photo = findViewById(R.id.toolbar_photo);
        bottomNavigationView_photo = findViewById(R.id.navigation_bar_photo);
        setSupportActionBar(toolbar_photo);

        pathImage = getIntent().getStringExtra("path_image");
        Glide.with(this).load(pathImage).asBitmap().into(image);

        toolbar_photo.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_photo_setAs:
                        setAsWallpaper();
                        return true;
                    case R.id.action_photo_delete:
                        File target = new File(pathImage);
                        Log.d(" target_path", "" + pathImage);
                        if (target.exists() && target.isFile() && target.canWrite()) {
                            target.delete();
                            Log.d("d_file", "" + target.getName());
                        }
                        return true;

                    case R.id.action_photo_share:
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                        Bitmap bitmap = bitmapDrawable.getBitmap();
                        shareImage(bitmap);
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

            private void setAsWallpaper() {
                WallpaperManager myWallpaperManager
                        = WallpaperManager.getInstance(getApplicationContext());
                try {
                    BitmapDrawable bitmapDrawable = (BitmapDrawable) image.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    myWallpaperManager.setBitmap(bitmap);
                    Toast.makeText(DisplayFullImageActivity.this, "Set background successfully!", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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
                    case R.id.photo_navigation_info:
                        listInfoPhoto=ShowInforPhotos.listOfImageVideos(getApplicationContext(),pathImage);
                        FragmentManager fm = getSupportFragmentManager();
                        PhotoInformationFragment photoInformationFragment = PhotoInformationFragment.newInstance("Photo Information", "Dialog");
                        photoInformationFragment.show(fm, null);
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

    public void callBroadCast() {
        if (Build.VERSION.SDK_INT >= 14) {
            Log.e("-->", " >= 14");
            MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                /*
                 *   (non-Javadoc)
                 * @see android.media.MediaScannerConnection.OnScanCompletedListener#onScanCompleted(java.lang.String, android.net.Uri)
                 */
                public void onScanCompleted(String path, Uri uri) {
                    Log.e("ExternalStorage", "Scanned " + path + ":");
                    Log.e("ExternalStorage", "-> uri=" + uri);
                }
            });
        } else {
            Log.e("-->", " < 14");
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }


    //Using to send pathImage to PhotoInformation
    public String getPathImage()
    {
        return pathImage;
    }

    public ArrayList<String> getListInfoPhoto() {
        return listInfoPhoto;
    }
}