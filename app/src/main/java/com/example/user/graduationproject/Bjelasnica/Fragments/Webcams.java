package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.WebCamLinks;
import com.example.user.graduationproject.Bjelasnica.WebCams.WebCam;
import com.example.user.graduationproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class Webcams extends Fragment {
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private InternetConnection internetConnection = new InternetConnection();
    private List<WebCamLinks> list = new ArrayList<>();
    private String han_url;
    private ImageButton btnPlayPause;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webcams, container, false);
        btnPlayPause = v.findViewById(R.id.btn_play_pause);
        if(internetConnection.getInternetConnection()==true){
            firebaseHolder.getDatabaseReferenceForWebCams().addChildEventListener(childEventListener());
        }
        else {
            Toast.makeText(getActivity(), "Please connect on the internet", Toast.LENGTH_SHORT).show();
        }
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(internetConnection.getInternetConnection() == true){
                    firebaseHolder.getDatabaseReferenceForWebCams().addChildEventListener(childEventListener());
                    Intent intent = new Intent(getActivity(), WebCam.class);
                    intent.putExtra("URL_LINK", han_url);
                    startActivity(intent);
                    int a = 0;
                }
                else{
                    Toast.makeText(getActivity(), "Can't play video without internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ImageView imageView = v.findViewById(R.id.hotel_lavina);
        Glide.with(getContext()).load(R.drawable.hotel_lavina).into(imageView);
        return v;
    }

    private ChildEventListener childEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String url = dataSnapshot.getValue(String.class);
                han_url = url;
                int a = 0;
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

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
