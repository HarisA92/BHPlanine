package com.bhplanine.user.graduationproject.fragments.navigationFragments.expandListFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.utils.FirebaseHolder;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ContentFragment extends Fragment {

    private static final String MOUNTAINT_ACCOMMODATION = "Accommodation";

    public static ContentFragment newInstance(String param1) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(MOUNTAINT_ACCOMMODATION, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);

        String mountaint = Objects.requireNonNull(getArguments()).getString(MOUNTAINT_ACCOMMODATION);
        Objects.requireNonNull(getActivity()).setTitle(mountaint);

        FirebaseHolder firebaseHolder = new FirebaseHolder(getActivity());
        InternetConnection internetConnection = new InternetConnection();
        if(internetConnection.getInternetConnection()) {
            switch (Objects.requireNonNull(mountaint)) {
                case "Bjelašnica":
                    firebaseHolder.getDatabaseReferenceForAccommodation("Bjelašnica Accommodation").addValueEventListener(valueEventListener());
                case "Jahorina":
                    firebaseHolder.getDatabaseReferenceForAccommodation("Jahorina Accommodation").addValueEventListener(valueEventListener());
                case "Ravna Planina":
                    firebaseHolder.getDatabaseReferenceForAccommodation("Ravna Planina Accommodation").addValueEventListener(valueEventListener());
                case "Vlašić":
                    firebaseHolder.getDatabaseReferenceForAccommodation("Vlašić Accommodation").addValueEventListener(valueEventListener());
                case "Igman":
                    firebaseHolder.getDatabaseReferenceForAccommodation("Igman Accommodation").addValueEventListener(valueEventListener());
            }
        }else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private ValueEventListener valueEventListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

}
