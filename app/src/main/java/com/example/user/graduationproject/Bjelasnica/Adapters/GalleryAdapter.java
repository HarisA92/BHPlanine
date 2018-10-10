package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.graduationproject.Bjelasnica.ViewPager.ImagePopUp;
import com.example.user.graduationproject.Bjelasnica.Utils.GalleryImageHolder;
import com.example.user.graduationproject.R;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private static final String POSITION = "position";

    private final List<GalleryImageHolder> list;
    private final Context context;

    public GalleryAdapter(final List<GalleryImageHolder> list, final Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        final int adapterPosition = holder.getAdapterPosition();
        holder.imageView.setImageResource(list.get(adapterPosition).getImageId());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ImagePopUp.class);
                intent.putExtra(POSITION, adapterPosition);
                v.getContext().startActivity(intent);
            }
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
