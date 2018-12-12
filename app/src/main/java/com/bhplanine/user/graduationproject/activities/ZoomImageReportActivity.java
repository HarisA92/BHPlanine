package com.bhplanine.user.graduationproject.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bhplanine.user.graduationproject.R;
import com.github.chrisbanes.photoview.PhotoView;


public class ZoomImageReportActivity extends AppCompatActivity {

    private PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_report);
        onCreate();
        photoView = findViewById(R.id.photo_view);
        getImageReport();
    }

    private void getImageReport() {
        String imageReport = getIntent().getStringExtra(this.getResources().getString(R.string.POSITION));
        Glide.with(this).load(imageReport).into(photoView);
    }

    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
