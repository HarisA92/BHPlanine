package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.WebCams.WebCam;
import com.example.user.graduationproject.R;


public class Webcams extends Fragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webcams, container, false);
        ImageButton btnPlayPause = v.findViewById(R.id.btn_play_pause);
        ImageView imageView = v.findViewById(R.id.hotel_lavina);
        Glide.with(getContext()).load(R.drawable.hotel_lavina).into(imageView);
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebCam.class);
                startActivity(intent);
            }
        });
        return v;
    }
}
