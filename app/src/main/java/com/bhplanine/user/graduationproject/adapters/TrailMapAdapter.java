package com.bhplanine.user.graduationproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bhplanine.user.graduationproject.activities.ZoomImageTrailMapActivity;
import com.bhplanine.user.graduationproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailMapAdapter extends RecyclerView.Adapter<TrailMapAdapter.ViewHolder> {

    private final ArrayList<String> list;

    public TrailMapAdapter(final ArrayList<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailmap, parent, false);
        return new TrailMapAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        Picasso.with(context).load(list.get(position)).centerCrop().resize(1280, 720).into(holder.imageView);
        holder.imageView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ZoomImageTrailMapActivity.class);
            intent.putExtra(context.getResources().getString(R.string.POSITION), list.get(position));
            context.startActivity(intent);
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
