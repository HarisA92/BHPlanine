package com.example.user.graduationproject.Bjelasnica.ViewPager;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.user.graduationproject.Bjelasnica.Utils.GalleryImageHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Mountain;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImagePopUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pop_up);
        onCreate();
        ArrayList<GalleryImageHolder> galleryImageHolderArrayList = new ArrayList<>();
        ViewPager viewPager = findViewById(R.id.viewPager);
        Bundle extras = getIntent().getExtras();
        String image = extras.getString("position");
        GalleryImageHolder holder = new GalleryImageHolder(image);
        galleryImageHolderArrayList.add(holder);

        viewPager.setAdapter(new ImageViewer(this, galleryImageHolderArrayList));
        //viewPager.setCurrentItem(image);
        int a = 0;
    }

    public void onCreate() {
        getSupportActionBar().hide();
    }

    /*public List<GalleryImageHolder> getData(final Mountain mountain) {
        if (mountain == Mountain.BJELASNICA) return bjelasnica;
        return jahorina;
    }*/
}
