package com.example.memorybox;

import android.content.Context;
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
import java.nio.file.Files;
import java.util.List;

public class AddVideosToAlbumAdapter extends RecyclerView.Adapter<AddVideosToAlbumAdapter.albumHolder> {
    Context context;
    List<Album> albumList;
    public AddVideosToAlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }
    @NonNull
    @Override
    public albumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent,false);
        AddVideosToAlbumAdapter.albumHolder abh = new AddVideosToAlbumAdapter.albumHolder(view);
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

                //Còn nữa--phần lấy tên album
                String[] token=PlayVideoActivity.pathVideo.split("/");
                String fileImageOrigin=token[token.length-1];
                if(fileImageOrigin.endsWith("mp4")){
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
                    File imageFile=new File(path,fileImageOrigin);
                    try {
                        copyFile(new File(PlayVideoActivity.pathVideo),path);

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

    private void copyFile(File source,File destination) throws IOException{
        byte[] buffer=new byte[1024];
        FileInputStream fin=new FileInputStream(source);
        FileOutputStream fos=new FileOutputStream(destination);
        int read;
        while ((read=fin.read(buffer))!=-1){
            fos.write(buffer,0,read);
        }
        fos.close();
        fin.close();
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
