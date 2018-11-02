package com.example.user.graduationproject.Bjelasnica.Fragments;

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

import com.example.user.graduationproject.Bjelasnica.Adapters.LiftTicketAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.utils.LiftTicketHolder;
import com.example.user.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class LiftTickets extends Fragment {

    private ArrayList<LiftTicketHolder> arrayList = new ArrayList<>();
    private LiftTicketAdapter liftTicketAdapter;
    private String getMountain;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lift_tickets, container, false);

        getMountain = String.valueOf(getActivity().getTitle());

        InternetConnection internetConnection = new InternetConnection();
        FirebaseHolder firebaseHolder = new FirebaseHolder(getActivity());
        if (internetConnection.getInternetConnection()) {
            buildRecyclerView(v);
            firebaseHolder.getDatabaseReferenceForTicketPrice().addValueEventListener(valueEventListener());
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            buildRecyclerView(v);
        }
        return v;
    }

    private ValueEventListener valueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LiftTicketHolder value = postSnapshot.getValue(LiftTicketHolder.class);
                    arrayList.add(value);
                    saveUserReportPreferences(arrayList);
                }
                liftTicketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    private void buildRecyclerView(View v) {
        liftTicketAdapter = new LiftTicketAdapter(getContext(), arrayList);
        RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view_lift_tickets);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(liftTicketAdapter);
    }

    private void saveUserReportPreferences(ArrayList<LiftTicketHolder> liftTicketHolders) {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getMountain + getResources().getString(R.string.sharedPreferencesLiftTickets), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(liftTicketHolders);
            editor.putString(getMountain + getResources().getString(R.string.sharedPreferencesLiftTickets_list), json);
            editor.apply();
        }
    }

    private void loadUserReportPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getMountain + getResources().getString(R.string.sharedPreferencesLiftTickets), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getMountain + getResources().getString(R.string.sharedPreferencesLiftTickets_list), null);
        Type type = new TypeToken<ArrayList<LiftTicketHolder>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
    }
}
