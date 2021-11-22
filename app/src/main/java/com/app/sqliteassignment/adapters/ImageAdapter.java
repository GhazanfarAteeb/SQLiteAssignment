package com.app.sqliteassignment.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sqliteassignment.Activities.HomeScreenActivity;
import com.app.sqliteassignment.Activities.UpdateImageActivity;
import com.app.sqliteassignment.DatabaseHelper.DatabaseHelper;
import com.app.sqliteassignment.Models.ImageModel;
import com.app.sqliteassignment.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    Context context;
    List<ImageModel> imagesList;
    PassData passDataListener;
    public ImageAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_images_detail, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.ViewHolder holder, int position) {
        ImageModel imageData = imagesList.get(position);
        Glide.with(context).load(imageData.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.ivImage);
        holder.tvLocation.setText(imageData.getLocation());
        holder.tvLocationDescription.setText(imageData.getLocationDescription());
        holder.itemView.setOnClickListener(view ->{

            Bundle data = new Bundle();
            data.putInt(DatabaseHelper.COL_IMAGE_ID, imageData.getID());
            passDataListener.sendData(data);
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvLocation;
        TextView tvLocationDescription;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tvLocationDescription = itemView.findViewById(R.id.tv_location_description);
        }
    }

    public void setData(List<ImageModel> imagesList) {
        this.imagesList = imagesList;
        notifyDataSetChanged();
    }

    public void setPassDataListener(PassData passData) {
        this.passDataListener = passData;
    }

    public interface PassData {
        void sendData(Bundle bundle);
    }
}
