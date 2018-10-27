package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Adapters.LiftTicketAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.LiftTicketHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.R;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import static com.google.firebase.database.FirebaseDatabase.getInstance;


public class LiftTickets extends Fragment {
    private InternetConnection internetConnection = new InternetConnection();
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private ArrayList<LiftTicketHolder> arrayList = new ArrayList<>();
    private LiftTicketAdapter liftTicketAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lift_tickets, container, false);


        if(internetConnection.getInternetConnection() == true){
            buildRecyclerView(v);
           firebaseHolder.getDatabaseReferenceForTicketPrice().addValueEventListener(valueEventListener());
        }
        else{
            Toast.makeText(getActivity(), "Please connect on the internet!", Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            buildRecyclerView(v);
            }
        return v;
    }

    private ValueEventListener valueEventListener(){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    LiftTicketHolder value = postSnapshot.getValue(LiftTicketHolder.class);
                    arrayList.add(value);
                    saveUserReportPreferences(arrayList);
                }
                liftTicketAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
    }

    private void buildRecyclerView(View v){
        liftTicketAdapter = new LiftTicketAdapter(getContext(), arrayList);
        mRecyclerView = v.findViewById(R.id.recycler_view_lift_tickets);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(liftTicketAdapter);
    }

    private void saveUserReportPreferences(ArrayList<LiftTicketHolder> liftTicketHolders){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharepreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(liftTicketHolders);
        editor.putString("ticketprice", json);
        editor.apply();
    }

    private void loadUserReportPreferences(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharepreference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("ticketprice", null);
        Type type = new TypeToken<ArrayList<LiftTicketHolder>>(){}.getType();
        arrayList = gson.fromJson(json, type);
    }
}
