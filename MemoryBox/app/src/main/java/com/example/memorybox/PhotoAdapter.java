package com.example.memorybox;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frame_photo, parent, false);
        GridViewHolder gvHolder = new GridViewHolder(view);
        return gvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder holder, int position) {
        holder.imageView.setImageResource(photoList.get(position).getPhotoResource());
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photo_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("OnClicked", "GridViewHolder item clicked");
        }
    }
}
