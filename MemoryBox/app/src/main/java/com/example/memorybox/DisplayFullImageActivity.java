package com.example.memorybox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class DisplayFullImageActivity extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_full_image);
        image=findViewById(R.id.imageOther);
        String pathImage=getIntent().getStringExtra("path_image");
        Glide.with(this).load(pathImage).asBitmap().into(image);
    }
}