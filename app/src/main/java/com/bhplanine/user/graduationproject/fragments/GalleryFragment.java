package com.bhplanine.user.graduationproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.adapters.GalleryAdapter;
import com.bhplanine.user.graduationproject.utils.FirebaseHolder;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private FirebaseHolder firebaseHolder;
    private GalleryAdapter galleryAdapter;
    private ArrayList<String> imagesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gallery, container, false);
        buildRecyclerView(v);
        InternetConnection internetConnection = new InternetConnection();
        firebaseHolder = new FirebaseHolder(getActivity());

        if (internetConnection.getInternetConnection()) {
            firebaseHolder.getDatabaseReferenceForGallery().addChildEventListener(childEventListener());
            buildRecyclerAdapter();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private ChildEventListener childEventListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String url = dataSnapshot.getValue(String.class);
                imagesArrayList.add(url);
                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    private void buildRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void buildRecyclerAdapter(){
        galleryAdapter = new GalleryAdapter(imagesArrayList, getContext());
        recyclerView.setAdapter(galleryAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (firebaseHolder.getDatabaseReferenceForGallery() != null) {
            firebaseHolder.getDatabaseReferenceForGallery().removeEventListener(childEventListener());
        }
    }
}




