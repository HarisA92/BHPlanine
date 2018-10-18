package com.example.user.graduationproject.Bjelasnica.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.R;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.firebase.database.FirebaseDatabase.getInstance;


public class LiftTickets extends Fragment {
    private InternetConnection internetConnection = new InternetConnection();
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private DatabaseReference firebaseDatabase;
    private ArrayAdapter arrayAdapter;
    private ArrayAdapter arrayAdapter1;
    private ArrayList<String> dayList = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ListView listDays;
    private ListView listPrice;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lift_tickets, container, false);
        listDays = v.findViewById(R.id.list_viewTicket);
        listPrice = v.findViewById(R.id.list_viewTicket1);
        if(internetConnection.getInternetConnection() == true){
            buildArrayAdapter();
            buildArrayAdapter1();
            firebaseHolder.getDatabaseReferenceForTicketPrice().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                        String days = postSnapshot.child("Bjelasnica_days").getValue(String.class);
                        dayList.add(days);
                    }
                    //arrayAdapter.notifyDataSetChanged();
                    int a = 0;
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else{
            Toast.makeText(getActivity(), "Please connect on the internet!", Toast.LENGTH_SHORT).show();
            //loadUserReportPreferences();
        }
        return v;
    }

    private void saveUserReportPreferences(ArrayList<String> arrayList){
        sharedPreferences = getActivity().getSharedPreferences("ticket", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> set = new HashSet<String>();
        set.addAll(arrayList);
        editor.putStringSet("key", set);
        editor.commit();
    }

    private void loadUserReportPreferences(){
        Set<String> set = sharedPreferences.getStringSet("key", null);
    }

    private void buildArrayAdapter1(){
        arrayAdapter1 = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, priceList);
        listPrice.setAdapter(arrayAdapter1);
    }
    private void buildArrayAdapter(){
        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, dayList);
        listDays.setAdapter(arrayAdapter);
    }
}
