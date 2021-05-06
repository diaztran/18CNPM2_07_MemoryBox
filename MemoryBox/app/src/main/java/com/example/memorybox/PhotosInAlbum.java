package com.example.memorybox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class PhotosInAlbum extends AppCompatActivity {

    RecyclerView rv_photo_in_album;
    public static MemberAdp adapterMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_in_album);
        rv_photo_in_album=findViewById(R.id.rv_photo_in_album);

        String nameAlbum=getIntent().getStringExtra("nameAlbum");
        Toast.makeText(PhotosInAlbum.this,nameAlbum,Toast.LENGTH_LONG).show();
        Log.e("lll",nameAlbum);
        ArrayList<Photo> lstPhoto=HandleAlbum.getPhotosInAlbum(this,nameAlbum);
        Log.e("lll",lstPhoto.size()+"");
        adapterMember=new MemberAdp(lstPhoto,PhotosInAlbum.this); //còn sửa nữa
        GridLayoutManager gridLayoutManager=new GridLayoutManager(PhotosInAlbum.this,3);
        rv_photo_in_album.setLayoutManager(gridLayoutManager);
        rv_photo_in_album.setAdapter(adapterMember);

    }
}