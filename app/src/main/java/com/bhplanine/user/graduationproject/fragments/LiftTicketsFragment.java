package com.bhplanine.user.graduationproject.fragments;

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

import com.bhplanine.user.graduationproject.adapters.LiftTicketAdapter;
import com.bhplanine.user.graduationproject.utils.FirebaseHolder;
import com.bhplanine.user.graduationproject.models.LiftTicketHolder;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;


public class LiftTicketsFragment extends Fragment {

    private ArrayList<LiftTicketHolder> arrayList = new ArrayList<>();
    private LiftTicketAdapter liftTicketAdapter;
    private String getMountain;
    private FirebaseHolder firebaseHolder;
    private RecyclerView recyclerView;
    private ValueEventListener valueEventListener;

    //@AddTrace(name = "onCreateLiftTicketsFragment")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lift_tickets, container, false);
        buildRecyclerView(v);
        getMountain = String.valueOf(Objects.requireNonNull(getActivity()).getTitle());
        firebaseHolder = new FirebaseHolder();

        InternetConnection internetConnection = new InternetConnection();
        if (internetConnection.getInternetConnection()) {
            valueEventListener = valueEventListener();
            firebaseHolder.getDatabaseReferenceForTicketPrice().addValueEventListener(valueEventListener);
            buildRecyclerAdapter();
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            buildRecyclerAdapter();
        }
        return v;
    }

    private ValueEventListener valueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LiftTicketHolder value = postSnapshot.getValue(LiftTicketHolder.class);
                    arrayList.add(value);
                }
                saveUserReportPreferences(arrayList);
                liftTicketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
    }

    private void buildRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.recycler_view_lift_tickets);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
    }

    private void buildRecyclerAdapter(){
        liftTicketAdapter = new LiftTicketAdapter(arrayList);
        recyclerView.setAdapter(liftTicketAdapter);
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
        if(getActivity() != null){
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getMountain + getResources().getString(R.string.sharedPreferencesLiftTickets), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getMountain + getResources().getString(R.string.sharedPreferencesLiftTickets_list), null);
            Type type = new TypeToken<ArrayList<LiftTicketHolder>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(firebaseHolder.getDatabaseReferenceForTicketPrice() != null){
            firebaseHolder.getDatabaseReferenceForTicketPrice().removeEventListener(valueEventListener);
        }
    }
}
