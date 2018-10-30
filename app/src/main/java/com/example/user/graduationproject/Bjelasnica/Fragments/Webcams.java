package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.WebCams.WebCam;
import com.example.user.graduationproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


public class Webcams extends Fragment {

    private String han_url;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_webcams, container, false);

        final InternetConnection internetConnection = new InternetConnection();
        final FirebaseHolder firebaseHolder = new FirebaseHolder(getActivity());
        ImageButton btnPlayPause = v.findViewById(R.id.btn_play_pause);


        if (internetConnection.getInternetConnection()) {
            firebaseHolder.getDatabaseReferenceForWebCams().addChildEventListener(childEventListener());
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
        }
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (internetConnection.getInternetConnection()) {
                    firebaseHolder.getDatabaseReferenceForWebCams().addChildEventListener(childEventListener());
                    Intent intent = new Intent(getActivity(), WebCam.class);
                    intent.putExtra(getResources().getString(R.string.URL_LINK), han_url);
                    startActivity(intent);
                    int a = 0;
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
                }
            }
        });
        ImageView imageView = v.findViewById(R.id.hotel_lavina);
        Glide.with(v.getContext()).load(R.drawable.hotel_lavina).into(imageView);
        return v;
    }

    private ChildEventListener childEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                han_url = dataSnapshot.getValue(String.class);
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
}
