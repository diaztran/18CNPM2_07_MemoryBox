package com.example.memorybox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class PhotosInAlbum extends AppCompatActivity {
    public static int VIEWTYPE_GRID = 1;
    public static int VIEWTYPE_LIST = 2;

    RecyclerView rv_photo_in_album;
    private int viewType = VIEWTYPE_LIST;
    Toolbar inAlbumToolbar;
    public static MemberAdp adapterMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_in_album);

        rv_photo_in_album=findViewById(R.id.rv_photo_in_album);
        inAlbumToolbar = findViewById(R.id.in_album_toolbar);

        setSupportActionBar(inAlbumToolbar);
        getSupportActionBar().setTitle(null);



        String nameAlbum=getIntent().getStringExtra("nameAlbum");
        Toast.makeText(PhotosInAlbum.this,nameAlbum,Toast.LENGTH_LONG).show();
        Log.e("lll",nameAlbum);
        ArrayList<Photo> lstPhoto=HandleAlbum.getPhotosInAlbum(this,nameAlbum);
        Log.e("lll",lstPhoto.size()+"");
        adapterMember=new MemberAdp(lstPhoto,PhotosInAlbum.this); //còn sửa nữa
        GridLayoutManager gridLayoutManager=new GridLayoutManager(PhotosInAlbum.this,3);
        //rv_photo_in_album.setLayoutManager(gridLayoutManager);


        if (this.viewType == VIEWTYPE_LIST) {
            rv_photo_in_album.setLayoutManager(new LinearLayoutManager(this));
        } else rv_photo_in_album.setLayoutManager(new GridLayoutManager(this, 3));

        rv_photo_in_album.setAdapter(adapterMember);

        inAlbumToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.in_album_action_viewtype_grid:
                        viewType = VIEWTYPE_GRID;
                        rv_photo_in_album.setLayoutManager(new GridLayoutManager(PhotosInAlbum.this, 3));
                        return true;
                    case R.id.in_album_action_viewtype_list:
                        viewType = VIEWTYPE_LIST;
                        rv_photo_in_album.setLayoutManager(new LinearLayoutManager(PhotosInAlbum.this));
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.in_album_option_menu, menu);
        return true;
    }
}