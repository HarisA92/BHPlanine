package com.bhplanine.user.graduationproject.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bhplanine.user.graduationproject.activities.ZoomImageGallery;
import com.bhplanine.user.graduationproject.R;

import java.util.ArrayList;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private final ArrayList<String> list;

    public GalleryAdapter(final ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Context context = holder.itemView.getContext();
        Glide.with(context).load(list.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener( view -> {
            Intent intent = new Intent(view.getContext(), ZoomImageGallery.class);
            intent.putExtra(context.getResources().getString(R.string.POSITION), list.get(position));
            view.getContext().startActivity(intent);
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
