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
        uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String [] projection={MediaStore.MediaColumns.DATA,MediaStore.Video.Media.BUCKET_DISPLAY_NAME,MediaStore.Video.Media._ID, MediaStore.Video.Thumbnails.DATA};

        String orderBy= MediaStore.Images.Media.DATE_TAKEN;

        cursor=context.getApplicationContext().getContentResolver().query(uri,projection,null,null,orderBy+" DESC");
        //                      trả về chỉ số của cột với tên chỉ định
        column_index_data=cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        //click
        thumb=cursor.getColumnIndexOrThrow(MediaStore.Video.Thumbnails.DATA);

        //get folder name
        while (cursor.moveToNext())
        {
            ablosutePathOfImage=cursor.getString(column_index_data);
            thumbnail=cursor.getString(thumb);
            Photo videoTemp=new Photo(ablosutePathOfImage,thumbnail);
            listOfAllImages.add(videoTemp);
        }

        uriImage= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String [] projectionImage={MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME};
        String orderByImage= MediaStore.Video.Media.DATE_TAKEN;
        cursorImage=context.getContentResolver().query(uriImage,projectionImage,null,null,orderByImage+" DESC");

        columIndexDataImage=cursorImage.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        thumbImage=cursorImage.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA);
        while (cursorImage.moveToNext())
        {
            Photo imageTemp=new Photo(cursorImage.getString(columIndexDataImage),cursorImage.getString(thumbImage));
            listOfAllImages.add(imageTemp);
        }

        return listOfAllImages;
    }
}
