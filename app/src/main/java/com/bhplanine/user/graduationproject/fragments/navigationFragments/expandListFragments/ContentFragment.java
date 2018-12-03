package com.bhplanine.user.graduationproject.fragments.navigationFragments.expandListFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.adapters.AccommodationAdapter;
import com.bhplanine.user.graduationproject.models.AccommodationHolder;
import com.bhplanine.user.graduationproject.retrofit.model.WeatherDay;
import com.bhplanine.user.graduationproject.utils.FirebaseHolder;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContentFragment extends Fragment {

    private static final String MOUNTAINT_ACCOMMODATION = "Accommodation";
    private RecyclerView mRecyclerView;
    private AccommodationAdapter adapter;
    private ArrayList<AccommodationHolder> list = new ArrayList<>();
    private String mountaint;

    public static ContentFragment newInstance(String param1) {
        ContentFragment fragment = new ContentFragment();
        Bundle args = new Bundle();
        args.putString(MOUNTAINT_ACCOMMODATION, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_content, container, false);
        buildRecyclerView(v);
        mountaint = Objects.requireNonNull(getArguments()).getString(MOUNTAINT_ACCOMMODATION);
        Objects.requireNonNull(getActivity()).setTitle(mountaint);

        FirebaseHolder firebaseHolder = new FirebaseHolder(getActivity());
        InternetConnection internetConnection = new InternetConnection();
        if (internetConnection.getInternetConnection()) {
            if(mountaint != null){
                switch (mountaint) {
                    case "Bjelašnica":
                        firebaseHolder.getDatabaseReferenceForAccommodation("Bjelasnica Accommodation").addValueEventListener(valueEventListener());
                        break;
                    case "Jahorina":
                        firebaseHolder.getDatabaseReferenceForAccommodation("Jahorina Accommodation").addValueEventListener(valueEventListener());
                        break;
                    case "Ravna Planina":
                        firebaseHolder.getDatabaseReferenceForAccommodation("Ravna Planina Accommodation").addValueEventListener(valueEventListener());
                        break;
                    case "Vlašić":
                        firebaseHolder.getDatabaseReferenceForAccommodation("Vlasic Accommodation").addValueEventListener(valueEventListener());
                        break;
                    case "Igman":
                        firebaseHolder.getDatabaseReferenceForAccommodation("Igman Accommodation").addValueEventListener(valueEventListener());
                        break;
                }
            }
        } else {
            loadUserReportPreferences();
            buildRecyclerAdapter();
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
        }
        return v;
    }

    private void saveUserReportPreferences(ArrayList<AccommodationHolder> accommodationList) {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(mountaint + getResources().getString(R.string.sharedPreferencesAccommodation), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(accommodationList);
            editor.putString(getResources().getString(R.string.sharedPreferencesAccommodation_list), json);
            editor.apply();
        }
    }

    private void loadUserReportPreferences() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(mountaint + getResources().getString(R.string.sharedPreferencesAccommodation), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getResources().getString(R.string.sharedPreferencesAccommodation_list), null);
            Type type = new TypeToken<ArrayList<AccommodationHolder>>() {
            }.getType();
            list = gson.fromJson(json, type);
        }
    }

    private ValueEventListener valueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postDatasnapshot : dataSnapshot.getChildren()){
                    AccommodationHolder holder = postDatasnapshot.getValue(AccommodationHolder.class);
                    list.add(holder);
                }
                buildRecyclerAdapter();
                saveUserReportPreferences(list);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recycler_view_content);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void buildRecyclerAdapter() {
        adapter = new AccommodationAdapter(list, getContext());
        mRecyclerView.setAdapter(adapter);
    }

}
