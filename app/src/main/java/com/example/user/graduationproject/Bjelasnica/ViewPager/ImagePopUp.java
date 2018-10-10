package com.example.user.graduationproject.Bjelasnica.ViewPager;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.user.graduationproject.Bjelasnica.Utils.GalleryImageHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Mountain;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.R;

import java.util.Arrays;
import java.util.List;

import static com.example.user.graduationproject.Bjelasnica.Utils.GalleryImageHolder.of;

public class ImagePopUp extends AppCompatActivity {
    private final List<GalleryImageHolder> bjelasnica = Arrays.asList(
            of(R.drawable.bjelasnica1),
            of(R.drawable.bjelasnica2),
            of(R.drawable.bjelasnica3),
            of(R.drawable.bjelasnica4),
            of(R.drawable.bjelasnica5),
            of(R.drawable.bjelasnica6),
            of(R.drawable.bjelasnica7),
            of(R.drawable.bjelasnica8)
    );

    private final List<GalleryImageHolder> jahorina = Arrays.asList(
            of(R.drawable.jahorina1),
            of(R.drawable.jahorina2),
            of(R.drawable.jahorina3),
            of(R.drawable.jahorina4),
            of(R.drawable.jahorina5),
            of(R.drawable.jahorina6),
            of(R.drawable.jahorina7),
            of(R.drawable.jahorina8)
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pop_up);
        onCreate();

        ViewPager viewPager = findViewById(R.id.viewPager);
        int image = getIntent().getIntExtra("position", 0);
        viewPager.setAdapter(new ImageViewer(this, getData(SkiResortHolder.getSkiResort().getMountain())));
        viewPager.setCurrentItem(image);
    }

    public void onCreate() {
        getSupportActionBar().hide();
    }

    public List<GalleryImageHolder> getData(final Mountain mountain) {
        if (mountain == Mountain.BJELASNICA) return bjelasnica;
        return jahorina;
    }
}
