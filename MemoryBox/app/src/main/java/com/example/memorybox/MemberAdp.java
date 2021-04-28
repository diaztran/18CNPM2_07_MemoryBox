package com.example.memorybox;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MemberAdp extends RecyclerView.Adapter<MemberAdp.PhotoHolder> {
    List<Photo> arrayListMember;
    private PhotoListener photoListener;

    Context context;

    public MemberAdp(List<Photo> arrayListMember, Context context, PhotoListener photoListener) {
        this.arrayListMember = arrayListMember;
        this.context = context;
        this.photoListener = photoListener;
    }

    public MemberAdp() {

    }

    public List<Photo> getSelectedPhoto() {
        List<Photo> selectedPhotos = new ArrayList<>();
        for (Photo p : arrayListMember) {
            if (p.isSelected) {
                selectedPhotos.add(p);
            }
        }
        return selectedPhotos;
    }

    @NonNull
    @Override
    public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_member, parent, false);
        return new MemberAdp.PhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
        //set member name on TextView
//        holder.tvName.setText(arrayListMember.get(position));
        Photo thisPhoto = arrayListMember.get(position);
        Glide.with(context).load(thisPhoto.getThumb()).into(holder.img_video);
//        Xóa hình

        if (thisPhoto.isSelected) {
//            viewBackground.setBackgroundResource(R.drawable.photo_selected_background);
            holder.imageSelected.setVisibility(View.VISIBLE);
        } else {
//            viewBackground.setBackgroundResource(R.drawable.photo_background);
            holder.imageSelected.setVisibility(View.GONE);
        }

//        hiển thị ảnh,video chi tiết khi click vào
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                PhotoFragment.positionPhotos = position;
                PhotoFragment.getDatePhotos = ShowInforPhotos.convertPathToDate(thisPhoto);
//                Log.e("position Image",""+PhotoFragment.positionImage);
                String[] parseLink = thisPhoto.getPath().split("/");
                String tParseLink = parseLink[parseLink.length - 1];
                String[] tailParseLink = tParseLink.split("\\.");
                String nameTail = tailParseLink[tailParseLink.length - 1];

                int p = arrayListMember.indexOf(thisPhoto);
                Intent intent;
                if (!nameTail.equals("mp4")) // Nếu khác đuôi MP4, tức là ảnh.
                {
                    // Nếu không có ảnh nào đang được chọn (Nếu có ít nhất một ảnh được chọn, thì ngăn mở Activity Show ảnh, thay vào đó set VISIBILITY cho checkbox)
                    // Thì mở Activity show ảnh như bình thường
                    if (getSelectedPhoto().size() == 0) {
                        Log.e("SELECTED PHOTOS: ", String.valueOf(getSelectedPhoto().size()));
                        //Display separate Image
                        Log.e("lofi arr", thisPhoto.getPath());
                        intent = new Intent(context, DisplayFullImageActivity.class);
                        intent.putExtra("path_image", thisPhoto.getPath());
                        intent.putExtra("thumb_image", thisPhoto.getThumb());

                        if (intent == null) {
                            Log.e("Find Null", "Intent in memAdap is Null");
                        }
                        context.startActivity(intent);
                    } else { // Nếu có ít nhất một ảnh đang được chọn, ngăn việc mở Activity show ảnh bằng click vào ảnh.
                        // Nếu ảnh đang được chọn
                        if (thisPhoto.isSelected) {
                            thisPhoto.setSelected(false); // Thì bỏ checkbox
                            holder.imageSelected.setVisibility(View.GONE);
                        } else { // Nếu không, hiện checkbox ảnh đã được chọn.
                            thisPhoto.setSelected(true);
                            holder.imageSelected.setVisibility(View.VISIBLE);
                        }
                    }
                    Log.e("SELECTED PHOTOS: ", String.valueOf(getSelectedPhoto().size()));
                } else if (nameTail.equals("mp4")) // Đuôi là mp4, tức là video items
                {
                    // Nếu chưa có ít nhất một item đang được chọn
                    if (getSelectedPhoto().size() == 0) {
                        Log.e("SELECTED PHOTOS: ", String.valueOf(getSelectedPhoto().size()));
                        //Display separate Video
                        intent = new Intent(context, PlayVideoActivity.class);
                        intent.putExtra("path_video", thisPhoto.getPath());
                        intent.putExtra("thumb_video", thisPhoto.getThumb());
                        context.startActivity(intent);
                    } else { // Tương tự như bên Photo item
                        if (thisPhoto.isSelected) {
                            thisPhoto.setSelected(false);
                            holder.imageSelected.setVisibility(View.GONE);
                        } else {
                            thisPhoto.setSelected(true);
                            holder.imageSelected.setVisibility(View.VISIBLE);
                        }
                    }
                    Log.e("SELECTED PHOTOS: ", String.valueOf(getSelectedPhoto().size()));
                }
            }
        });

        holder.layoutItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (thisPhoto.isSelected) {
//                    viewBackground.setBackgroundResource(R.drawable.photo_selected_background);
                    holder.imageSelected.setVisibility(View.GONE);
                    thisPhoto.isSelected = false;
                    Log.e("SELECTED PHOTOS: ", String.valueOf(getSelectedPhoto().size()));
                    if (getSelectedPhoto().size() == 0) {
                        photoListener.onPhotoShowAction(false);
                    }
                } else {
//                    viewBackground.setBackgroundResource(R.drawable.photo_background);
                    holder.imageSelected.setVisibility(View.VISIBLE);
                    thisPhoto.isSelected = true;
                    Log.e("T/F", String.valueOf(thisPhoto.isSelected));
                    Log.e("SELECTED PHOTOS: ", String.valueOf(getSelectedPhoto().size()));
                    photoListener.onPhotoShowAction(true);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayListMember.size();
    }

    public void removeItem(int position) {
        Log.e("star 1", "" + position);
        Log.e("star 11", "" + arrayListMember.size());
        arrayListMember.remove(position);
        notifyDataSetChanged();
        this.notifyItemRemoved(position);
//        notifyItemRangeChanged(position,getItemCount());
        Log.e("star 21", "" + arrayListMember.size());
        Log.e("star 2", "" + position);
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        ImageView img_video;
        ConstraintLayout layoutItem;
        ImageView imageSelected;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            img_video = itemView.findViewById(R.id.img_video);
            layoutItem = itemView.findViewById(R.id.layout_item);
            imageSelected = itemView.findViewById(R.id.imagePhotoSelected);
        }
    }
}

