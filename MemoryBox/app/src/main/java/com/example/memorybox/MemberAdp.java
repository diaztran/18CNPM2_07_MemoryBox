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

public class MemberAdp extends RecyclerView.Adapter<MemberAdp.ViewHolder>  {
    List<Photo> arrayListMember;
    Context context;

    public MemberAdp(List<Photo> arrayListMember, Context context) {
        this.arrayListMember = arrayListMember;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_member,parent,false);
        return new MemberAdp.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //set member name on TextView
//        holder.tvName.setText(arrayListMember.get(position));
        Photo video= arrayListMember.get(position);
        Glide.with(context).load(video.getThumb()).into(holder.img_video);
//        hiển thị ảnh,video chi tiết khi click vào
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoFragment.positionPhotos=position;
                PhotoFragment.getDatePhotos=ShowInforPhotos.convertPathToDate(video);
                String[] parseLink=video.getPath().split("/");
                String tParseLink=parseLink[parseLink.length-1];
                String[] tailParseLink=tParseLink.split("\\.");
                String nameTail=tailParseLink[tailParseLink.length-1];

                Intent intent;
                if(!nameTail.equals("mp4")) //Là ảnh Xong
                {
                    //Display separate Image
                    intent = new Intent(context, DisplayFullImageActivity.class);
                    intent.putExtra("path_image", video.getPath());
                    if (intent == null) {Log.e("Find Null", "Intent in memAdap is Null");}
                    context.startActivity(intent);
;
                }
                else // Là mp4
                {
                    //Display separate Video
                    intent = new Intent(context, PlayVideoActivity.class);
                    intent.putExtra("path_video",video.getPath());
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListMember.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_video;
        ConstraintLayout layoutItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_video=itemView.findViewById(R.id.img_video);
            layoutItem=itemView.findViewById(R.id.layout_item);
        }
    }
}

