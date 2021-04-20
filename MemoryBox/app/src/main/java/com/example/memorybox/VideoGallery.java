package com.example.memorybox;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class VideoGallery {
    public static ArrayList<Photo> listOfImages(Context context)
    {
        Uri uri;
        Uri uriImage;
        Cursor cursor;
        Cursor cursorImage;
        int column_index_data,thumb;
        int columIndexDataImage,thumbImage;
        ArrayList<Photo> listOfAllImages=new ArrayList<>();
        String ablosutePathOfImage;
        String thumbnail;
        //LOAD IMAGE
        uriImage=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projectionImage={MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderByImage=MediaStore.Images.Media.DATE_TAKEN;
        cursorImage=context.getContentResolver().query(uriImage,projectionImage,null,null,orderByImage+" DESC");
        columIndexDataImage=cursorImage.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        while(cursorImage.moveToNext())
        {
            Photo imageTemp=new Photo(cursorImage.getString(columIndexDataImage),cursorImage.getString(cursorImage.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA)));
            listOfAllImages.add(imageTemp);
        }
        cursorImage.close();

        //LOAD VIDEO
        uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String [] projection={MediaStore.MediaColumns.DATA,MediaStore.Video.Media.BUCKET_DISPLAY_NAME};
        String orderBy= MediaStore.Video.Media.DATE_TAKEN;
        cursor=context.getContentResolver().query(uri,projection,null,null,orderBy+" DESC");
        column_index_data=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        thumb=cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);
        //get folder name
        while (cursor.moveToNext())
        {
            ablosutePathOfImage=cursor.getString(column_index_data);
            thumbnail=cursor.getString(thumb);
            Photo videoTemp=new Photo(ablosutePathOfImage,thumbnail);
            listOfAllImages.add(videoTemp);
        }
        cursor.close();

        return listOfAllImages;


    }
}
