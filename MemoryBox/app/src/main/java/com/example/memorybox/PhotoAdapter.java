package com.example.memorybox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.GridViewHolder> implements PhotoListener{

    Context context;
    List<String> photoList;
    MemberAdp adapterMember;
    private PhotoListener photoListener;

    public PhotoAdapter(Context context, List<String> photoList, PhotoListener photoListener) {
        this.context = context;
        this.photoList = photoList;
        this.photoListener = photoListener;
    }
    public PhotoAdapter(Context context, List<String> photoList) {
        this.context = context;
        this.photoList = photoList;
    }
    public MemberAdp getAdapterMember(){
        return this.adapterMember;
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
        String nameDateVideo = photoList.get(position);
        holder.tvName.setText(nameDateVideo);
        List<Photo> arrayListMember = PhotoFragment.groupHashMap.get(nameDateVideo);

        //Init member adapter
        adapterMember = new MemberAdp(arrayListMember, context, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        holder.rvMember.setLayoutManager(gridLayoutManager);
        holder.rvMember.setAdapter(adapterMember);
    }


    @Override
    public int getItemCount() {
        return photoList.size();
    }

    @Override
    public void onPhotoShowAction(boolean isSelected) {
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        RecyclerView rvMember;
        LinearLayout linearLayoutParent;
        CardView cardViewParent;

        public GridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            rvMember = itemView.findViewById(R.id.rv_member);
            linearLayoutParent = itemView.findViewById(R.id.linearLayoutParent);
            cardViewParent = itemView.findViewById(R.id.cardiewPhoto);
        }
    }
}

