package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.graduationproject.R;

public class TrailMap extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trail_map, container, false);
        ImageView imageView = v.findViewById(R.id.trailmap);
        imageView.setImageResource(R.drawable.bjelasnica9);
        return v;
    }
}
