package com.example.user.graduationproject.Bjelasnica;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.R;
import com.github.chrisbanes.photoview.PhotoView;


public class ZoomImageReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_report);
        onCreate();
        PhotoView photoView = findViewById(R.id.photo_view);
        String image = getIntent().getStringExtra(ImageReportAdapter.IMAGE_ADAPTER);
        Glide.with(getApplicationContext()).load(image).into(photoView);
    }

    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
