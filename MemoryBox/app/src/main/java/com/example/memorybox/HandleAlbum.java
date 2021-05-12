package com.example.memorybox;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import com.example.memorybox.Album;
import com.example.memorybox.Photo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HandleAlbum {
    public static List<Album> getAlbum(Context mContext){
        List<Album> buckets = new ArrayList<>();
        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projection = {MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA};
        List<String> nameAlbum=new ArrayList<>();
        Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null){
            File file;
            while (cursor.moveToNext()){
                String bucketPath = cursor.getString(cursor.getColumnIndex(projection[0]));
                String fisrtImage = cursor.getString(cursor.getColumnIndex(projection[1]));
                file = new File(fisrtImage);
                if (file.exists()&&!nameAlbum.contains(bucketPath)) {
                    buckets.add(new Album(bucketPath, fisrtImage));
                    nameAlbum.add(bucketPath);
                }
            }
            cursor.close();
        }
        //Còn sửa nữa
        File file=new File(Environment.getExternalStorageDirectory(),"FavoriteAlbum");
//        File file=Environment.getExternalStoragePublicDirectory("FavoriteAlbum");//Creates app specific folder
        if(!file.exists())
        {
            file.mkdirs();
            buckets.add(new Album("FavoriteAlbum",""));
        }


        return buckets;
    }
    public static ArrayList<Photo> getPhotosInAlbum(Context context, String nameAlbum)
    {
        ArrayList<Photo> listOfAllImages=new ArrayList<>();
        Uri uri=MediaStore.Files.getContentUri("external");

        String []projection={MediaStore.Files.FileColumns.DATA,MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME};
        String selection=MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" =?";

        CursorLoader cursorLoader=new CursorLoader(context,uri,projection,selection,new String[]{nameAlbum},MediaStore.Files.FileColumns.DATE_ADDED+" DESC");
        Cursor cursor=cursorLoader.loadInBackground();
        while (cursor.moveToNext())
        {
            String path=cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
            if(path.endsWith("mp4"))
            {
                int thumb=cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
                listOfAllImages.add(new Photo(path,cursor.getString(thumb)));
            }
            else if(path.endsWith("jpg")||path.endsWith("png")||path.endsWith("gif")||path.endsWith("jpeg"))
            {
                int thumb=cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
                listOfAllImages.add(new Photo(path,cursor.getString(thumb)));
            }
        }
        cursor.close();
        return listOfAllImages;
    }
}
