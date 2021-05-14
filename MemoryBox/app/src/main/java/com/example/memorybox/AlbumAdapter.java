package com.example.memorybox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.albumRowHolder> {
    Context context;
    List<Album> albumList;

    public AlbumAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public albumRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album,parent,false);
        albumRowHolder arh = new albumRowHolder(view);
        return arh;
    }

    @Override
    public void onBindViewHolder(@NonNull albumRowHolder holder, int position) {
        Log.e("lofimember"+position,position+"");
        Album album=albumList.get(position);
        holder.tvFolderName.setText(album.getName().toString());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumName=album.getName();
                Intent intent=new Intent(context,PhotosInAlbum.class);
                intent.putExtra("nameAlbum",albumName);
                try {
//                    Toast.makeText(context,albumName,Toast.LENGTH_LONG).show();
                    Log.e("haha",albumName);
                    context.startActivity(intent);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public static class albumRowHolder extends RecyclerView.ViewHolder {
        TextView tvFolderName;
        LinearLayout layoutItem;
        public albumRowHolder(@NonNull View itemView) {
            super(itemView);
            tvFolderName = itemView.findViewById(R.id.tvFolderName);
            layoutItem = itemView.findViewById(R.id.linearlayout_folder);
        }
    }
}
