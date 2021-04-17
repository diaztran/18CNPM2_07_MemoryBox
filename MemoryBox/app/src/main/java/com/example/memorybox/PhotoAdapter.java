package com.example.memorybox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.GridViewHolder> {

    Context context;
    List<Photo> photoList;

    public PhotoAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video, parent, false);
        GridViewHolder gvHolder = new GridViewHolder(view);
        return gvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        Photo video= photoList.get(position);
        //Đổ dữ liệu lên photos tab
        Glide.with(context).load(video.getThumb()).into(holder.imgVideo);

        //hiển thị ảnh,video chi tiết khi click vào
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] parseLink=video.getPath().split("/");
                String tParseLink=parseLink[parseLink.length-1];
                String[] tailParseLink=tParseLink.split("\\.");
                String nameTail=tailParseLink[tailParseLink.length-1];

                if(!nameTail.equals("mp4")) //Là ảnh Xong
                {
                    //Display separate Image
                    Intent intent=new Intent(context,DisplayFullImageActivity.class);
                    intent.putExtra("path_image",video.getPath());
                    context.startActivity(intent);
                }
                else // Là mp4
                {
                    //Display separate Video
                    Intent intent=new Intent(context,PlayVideoActivity.class);
                    intent.putExtra("path_video",video.getPath());
                    context.startActivity(intent);
                }
            }
        });
//        holder.imageView.setImageResource(photoList.get(position).getPhotoResource());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imgVideo;
        private ConstraintLayout layoutItem;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVideo = itemView.findViewById(R.id.img_video);
            layoutItem=itemView.findViewById(R.id.layout_item);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("OnClicked", "GridViewHolder item clicked");
        }
    }
}
