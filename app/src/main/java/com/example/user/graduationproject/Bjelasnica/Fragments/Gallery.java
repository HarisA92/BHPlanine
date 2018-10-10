package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.graduationproject.Bjelasnica.Adapters.GalleryAdapter;
import com.example.user.graduationproject.Bjelasnica.Utils.GalleryImageHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.ViewPager.ImagePopUp;
import com.example.user.graduationproject.R;

import java.util.List;

public class Gallery extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v  = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        ImagePopUp mImagePopUp = new ImagePopUp();
        List<GalleryImageHolder> images = mImagePopUp.getData(SkiResortHolder.getSkiResort().getMountain());

        GalleryAdapter adapter = new GalleryAdapter(images, getContext());
        recyclerView.setAdapter(adapter);
        return v;
    }
}
