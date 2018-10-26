package com.example.user.graduationproject.Bjelasnica;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Adapters.TrailMapAdapter;
import com.example.user.graduationproject.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class ZoomImageTrailMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image_trail_map);

        PhotoView photoView = findViewById(R.id.photo_view);
        String trailmap = getIntent().getStringExtra(TrailMapAdapter.TRAIL_MAP_IMAGE);
        Glide.with(getApplicationContext()).load(trailmap).into(photoView);
    }


}
