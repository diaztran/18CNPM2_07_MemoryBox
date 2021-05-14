package com.example.memorybox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class AddPhotosToAlbumAdapter extends RecyclerView.Adapter<AddPhotosToAlbumAdapter.albumHolder> {
    Context context;
    List<Album> albumList;

    public AddPhotosToAlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public albumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent,false);
        albumHolder abh = new albumHolder(view);
        return abh;
    }

    @Override
    public void onBindViewHolder(@NonNull albumHolder holder, int position) {
        Log.e("lofimember"+position,position+"");
        Album album = albumList.get(position);
        holder.tvFolderName.setText(album.getName().toString());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumName=album.getName();
//                Toast.makeText(context,albumName,Toast.LENGTH_LONG).show();
                //Còn nữa--phần lấy tên album
                String[] token=DisplayFullImageActivity.pathImage.split("/");
                String fileImageOrigin=token[token.length-1];
                if(fileImageOrigin.endsWith("jpg")||fileImageOrigin.endsWith("png")||fileImageOrigin.endsWith("gif")||fileImageOrigin.endsWith("jpeg")){
                    String[] token1=fileImageOrigin.split("\\.");
                    String imgName=token1[0];
                    //Create Path to save Image
                    File path= Environment.getExternalStoragePublicDirectory(albumName);//Creates app specific folder
                    if(!path.exists())
                    {
                        path.mkdirs();
                    }
                    else
                    {
                        Log.e("f love","favorite album is exit");
                    }
                    File imageFile=new File(path,imgName+".png");// Imagename.png
                    try {
                        FileOutputStream out=new FileOutputStream(imageFile);
                        DisplayFullImageActivity.image.setDrawingCacheEnabled(true);
                        Bitmap bm =DisplayFullImageActivity.image.getDrawingCache();
                        bm.compress(Bitmap.CompressFormat.PNG,100,out); //Compress Image

                        out.flush();
                        out.close();
                        // Dùng để sao chép ảnh. K dùng kiểu copy file thông thường
                        MediaScannerConnection.scanFile(context, new String[]{imageFile.getAbsolutePath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                            @Override
                            public void onScanCompleted(String path, Uri uri) {

                            }
                        });
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(context,albumName,Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class albumHolder extends RecyclerView.ViewHolder {
        TextView tvFolderName;
        LinearLayout layoutItem;
        public albumHolder(@NonNull View itemView) {
            super(itemView);
            tvFolderName = itemView.findViewById(R.id.tvFolderName);
            layoutItem = itemView.findViewById(R.id.linearlayout_folder);
        }
    }
}
/*
*                 else if(fileImageOrigin.endsWith(".mp4"))
                {
                    //Create Path to save Image
                    File path= Environment.getExternalStoragePublicDirectory(albumName);//Creates app specific folder
                    if(!path.exists())
                    {
                        path.mkdirs();
                    }
                    else
                    {
                        Log.e("f love","favorite album is exit");
                    }
                    File imageFile=new File(path,fileImageOrigin);// abc.mp4
                    try {
                        InputStream in=new FileInputStream()
                        FileOutputStream out=new FileOutputStream(imageFile);
                        out.write();
                        out.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
* */