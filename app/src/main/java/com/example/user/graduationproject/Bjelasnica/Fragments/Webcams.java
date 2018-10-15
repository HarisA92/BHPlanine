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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.WebCamLinks;
import com.example.user.graduationproject.Bjelasnica.WebCams.WebCam;
import com.example.user.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Webcams extends Fragment {
    private DatabaseReference databaseReference;
    private InternetConnection internetConnection = new InternetConnection();
    private List<WebCamLinks> list = new ArrayList<>();
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webcams, container, false);
        if(internetConnection != null){
            databaseReference = FirebaseDatabase.getInstance().getReference(getLiveStream());
            databaseReference.addValueEventListener(valueEventListener(list));
        }else{
            Toast.makeText(getActivity(), "Please connect on the internet", Toast.LENGTH_SHORT).show();
        }

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

    private ValueEventListener valueEventListener(final List<WebCamLinks> webLinks) {
        return new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                webLinks.clear();
                WebCamLinks webCamLinks = dataSnapshot.getValue(WebCamLinks.class);
                //Toast.makeText(getActivity(), "ovo je: " + webCamLinks.getBjelasnica_han(), Toast.LENGTH_SHORT).show();
                int a = 0;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    public String getLiveStream(){
        return String.format(SkiResortHolder.getSkiResort().getLiveStream());
    }
}
