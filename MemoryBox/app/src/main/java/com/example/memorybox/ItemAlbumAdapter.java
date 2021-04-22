package com.example.memorybox;

import android.content.Context;
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

public class ItemAlbumAdapter extends RecyclerView.Adapter<ItemAlbumAdapter.ViewHolder> {
    Context context;
    List<Bucket> buckets;

    public ItemAlbumAdapter(Context context, List<Bucket> buckets) {
        this.context = context;
        this.buckets = buckets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_albulm,parent,false);
        return new ItemAlbumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("lofimember"+position,position+"");
        Bucket bucket=buckets.get(position);
        holder.txtNameFolder.setText(bucket.getName().toString());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,bucket.getName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return buckets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameFolder;
        LinearLayout layoutItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameFolder=itemView.findViewById(R.id.txtNameFolder);
            layoutItem=itemView.findViewById(R.id.wholeFolder);
        }
    }
}
