package com.bhplanine.user.graduationproject.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bhplanine.user.graduationproject.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomImageGallery extends AppCompatActivity {

    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image_gallery);
        onCreate();
        photoView = findViewById(R.id.photo_view);
        getGalleryImage();
    }

    private void getGalleryImage() {
        String gallery = getIntent().getStringExtra(this.getString(R.string.POSITION));
        Glide.with(this).load(gallery).into(photoView);
    }

    public void onCreate() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }
}
