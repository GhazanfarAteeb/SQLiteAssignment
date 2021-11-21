package com.app.sqliteassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.sqliteassignment.Models.ImageModel;
import com.app.sqliteassignment.R;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder>{
    Context context;
    List<ImageModel> imagesList;
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
        holder.ivImage.setImageBitmap(imageData.getImage());
        holder.tvLocation.setText(imageData.getLocation());
        holder.tvLocationDescription.setText(imageData.getLocationDescription());
        holder.itemView.setOnClickListener(view ->{
            Toast.makeText(context,"I am clicked"+position,Toast.LENGTH_SHORT).show();
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
}
