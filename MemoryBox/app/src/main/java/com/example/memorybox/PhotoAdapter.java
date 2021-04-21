package com.example.memorybox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.GridViewHolder> {

    Context context;
//    List<Photo> photoList;
    List<String> photoList;
    public PhotoAdapter(Context context, List<String> photoList) {
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
        String nameDateVideo=photoList.get(position);
        holder.tvName.setText(nameDateVideo);
        List<Photo> arrayListMember=PhotoFragment.groupHashMap.get(nameDateVideo);
        //Init member adapter
        MemberAdp adapterMember=new MemberAdp(arrayListMember,context);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        holder.rvMember.setLayoutManager(gridLayoutManager);
        holder.rvMember.setAdapter(adapterMember);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName;
        RecyclerView rvMember;
        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tv_name);
            rvMember=itemView.findViewById(R.id.rv_member);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("OnClicked", "GridViewHolder item clicked");
        }
    }
}
