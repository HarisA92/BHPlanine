package com.example.user.graduationproject.Bjelasnica;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Adapters.GalleryAdapter;
import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Adapters.TrailMapAdapter;
import com.example.user.graduationproject.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


public class ZoomImageReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_report);
        onCreate();

        PhotoView photoView = findViewById(R.id.photo_view);

        String imageReport = getIntent().getStringExtra(ImageReportAdapter.IMAGE_ADAPTER);
        String gallery = getIntent().getStringExtra(GalleryAdapter.POSITION);

        Picasso.with(getApplicationContext()).load(gallery).resize(1920, 1080).onlyScaleDown().into(photoView);
        Glide.with(getApplicationContext()).load(imageReport).into(photoView);
    }

    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
