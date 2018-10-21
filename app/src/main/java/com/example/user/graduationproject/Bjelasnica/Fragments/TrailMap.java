package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Adapters.GalleryAdapter;
import com.example.user.graduationproject.Bjelasnica.Adapters.TrailMapAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class TrailMap extends Fragment {
    private InternetConnection internetConnection = new InternetConnection();
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private ArrayList<String> trailMapList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TrailMapAdapter trailMapAdapter;
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trail_map, container, false);
        /*ImageView imageView = v.findViewById(R.id.trailmap);
        imageView.setImageResource(R.drawable.bjelasnica9);*/
        if(internetConnection.getInternetConnection() == true){
            buildRecyclerView(v);
            firebaseHolder.getDatabaseReferenceForTrailMap().addChildEventListener(childEventListener());
        }
        else{
            Toast.makeText(getActivity(), "Please connect on the internet", Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private ChildEventListener childEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String downloadUrl = dataSnapshot.getValue(String.class);
                trailMapList.add(downloadUrl);
                trailMapAdapter.notifyDataSetChanged();
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

    private void buildRecyclerView(View v){
        recyclerView = v.findViewById(R.id.recycler_view_trail_map);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        trailMapAdapter = new TrailMapAdapter(trailMapList, getContext());
        recyclerView.setAdapter(trailMapAdapter);
    }
}
