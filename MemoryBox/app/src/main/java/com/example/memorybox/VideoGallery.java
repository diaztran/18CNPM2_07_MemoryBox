package com.example.memorybox;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.loader.content.CursorLoader;

import java.io.File;
import java.util.ArrayList;

public class VideoGallery {
    public static ArrayList<Photo> listOfImages(Context context)
    {
        ArrayList<Photo> listOfAllImages=new ArrayList<>();
        Uri uri=MediaStore.Files.getContentUri("external");

        String []projection={MediaStore.Files.FileColumns.DATA,MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME};
        String selection=MediaStore.Files.FileColumns.MEDIA_TYPE+"="+MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE+" OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        CursorLoader cursorLoader=new CursorLoader(context,uri,projection,selection,null,MediaStore.Files.FileColumns.DATE_ADDED+" DESC");
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
