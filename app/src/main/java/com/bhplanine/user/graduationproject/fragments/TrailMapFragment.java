package com.bhplanine.user.graduationproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.adapters.TrailMapAdapter;
import com.bhplanine.user.graduationproject.utils.FirebaseHolder;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class TrailMapFragment extends Fragment {

    private ArrayList<String> trailMapList = new ArrayList<>();
    private TrailMapAdapter trailMapAdapter;
    private FirebaseHolder firebaseHolder;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trail_map, container, false);
        buildRecyclerView(v);
        InternetConnection internetConnection = new InternetConnection();
        firebaseHolder = new FirebaseHolder(getActivity());

        if (internetConnection.getInternetConnection()) {
            firebaseHolder.getDatabaseReferenceForTrailMap().addChildEventListener(childEventListener());
            buildRecycerAdapter();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private ChildEventListener childEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                String downloadUrl = dataSnapshot.getValue(String.class);
                trailMapList.add(downloadUrl);
                trailMapAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void buildRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.recycler_view_trail_map);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        trailMapAdapter = new TrailMapAdapter(trailMapList, getContext());
        recyclerView.setAdapter(trailMapAdapter);
    }

    private void buildRecycerAdapter(){
        trailMapAdapter = new TrailMapAdapter(trailMapList, getContext());
        recyclerView.setAdapter(trailMapAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(firebaseHolder.getDatabaseReferenceForTrailMap() != null){
            firebaseHolder.getDatabaseReferenceForTrailMap().removeEventListener(childEventListener());
        }
    }
}
