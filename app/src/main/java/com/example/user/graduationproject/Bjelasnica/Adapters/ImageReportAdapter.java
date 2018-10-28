package com.example.user.graduationproject.Bjelasnica.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.graduationproject.Bjelasnica.ZoomImageReport;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageReportAdapter extends RecyclerView.Adapter<ImageReportAdapter.ImageViewHolder> {
    public static final String IMAGE_ADAPTER = "imageAdapter";
    private final Context mContext;
    private final ArrayList<Upload> mUploads;

    public ImageReportAdapter(final Context context, final ArrayList<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.user_report, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, final int position) {
        final Upload uploadCurrent = mUploads.get(position);
        holder.textViewName.setText(uploadCurrent.getName());
        holder.textViewUsername.setText(uploadCurrent.getmKey());
        holder.textTrailSituation.setText(uploadCurrent.getmTrail());
        holder.textSnow.setText(uploadCurrent.getmSnow());
        holder.date.setText(uploadCurrent.getDate());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ZoomImageReport.class);
                intent.putExtra(IMAGE_ADAPTER, uploadCurrent.getImageUrl());
                mContext.startActivity(intent);
            }
        });

        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewName;
        final ImageView imageView;
        final TextView textViewUsername;
        final TextView textTrailSituation;
        final TextView textSnow;
        final TextView date;

        ImageViewHolder(final View itemView) {
            super(itemView);
            textViewUsername = itemView.findViewById(R.id.Username);
            textViewName = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);
            textSnow = itemView.findViewById(R.id.textSnow);
            textTrailSituation = itemView.findViewById(R.id.textTrailSituation);
            date = itemView.findViewById(R.id.calendar);
        }
    }
}

