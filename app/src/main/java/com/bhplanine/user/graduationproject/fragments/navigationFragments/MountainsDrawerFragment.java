package com.bhplanine.user.graduationproject.fragments.navigationFragments;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.R;
import com.bhplanine.user.graduationproject.adapters.HomeAdapter;
import com.bhplanine.user.graduationproject.models.AllMountainInformationHolder;
import com.bhplanine.user.graduationproject.utils.FirebaseHolder;
import com.bhplanine.user.graduationproject.utils.InternetConnection;
import com.bhplanine.user.graduationproject.utils.SelectedFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.perf.metrics.AddTrace;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class MountainsDrawerFragment extends Fragment {

    private ArrayList<AllMountainInformationHolder> arrayList = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private RecyclerView mRecyclerView;
    private FirebaseHolder firebaseHolder;
    private ValueEventListener valueEventListener;
    private ProgressBar progressBar;
    private Context context;

    @AddTrace(name = "onCreateMountainDrawerFragment")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mountains, container, false);
        Objects.requireNonNull(getActivity()).setTitle("Mountains");
        context = getActivity();
        ((SelectedFragment) getActivity()).selectDrawerFragment(R.id.nav_mountains);
        buildRecyclerView(v);
        progressBar = v.findViewById(R.id.progress_bar_mountains);
        InternetConnection internetConnection = new InternetConnection(context);
        firebaseHolder = new FirebaseHolder();
        if (internetConnection.checkConnectivity()) {
            valueEventListener = valueEventListener();
            firebaseHolder.getDatabseReferenceForMountainInformation().orderByKey().addValueEventListener(valueEventListener);
            buildRecyclerAdapter();
        } else if(!internetConnection.checkConnectivity()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            buildRecyclerAdapterOfflineMode();
            progressBar.setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
        context = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView.setAdapter(null);
        mRecyclerView.setLayoutManager(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (valueEventListener != null) {
            firebaseHolder.getDatabseReferenceForMountainInformation().removeEventListener(valueEventListener);
        }

    }

    private void buildRecyclerView(View v) {
        mRecyclerView = v.findViewById(R.id.recycler_view_mountain);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void buildRecyclerAdapterOfflineMode() {
        if (arrayList != null) {
            homeAdapter = new HomeAdapter(arrayList);
            mRecyclerView.setAdapter(homeAdapter);
        }
    }

    private void buildRecyclerAdapter() {
        arrayList.clear();
        if (arrayList != null) {
            homeAdapter = new HomeAdapter(arrayList);
            mRecyclerView.setAdapter(homeAdapter);
        }
    }

    private ValueEventListener valueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AllMountainInformationHolder getValues = postSnapshot.getValue(AllMountainInformationHolder.class);
                    arrayList.add(getValues);
                }
                saveUserReportPreferences(arrayList);
                homeAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
    }

    private void saveUserReportPreferences(ArrayList<AllMountainInformationHolder> allMountainInformationHolders) {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferencesHome), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(allMountainInformationHolders);
            editor.putString(getResources().getString(R.string.sharedPreferencesHome_list), json);
            editor.apply();
        }
    }

    private void loadUserReportPreferences() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getResources().getString(R.string.sharedPreferencesHome), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getResources().getString(R.string.sharedPreferencesHome_list), null);
            Type type = new TypeToken<ArrayList<AllMountainInformationHolder>>() {
            }.getType();
            arrayList = gson.fromJson(json, type);
        }
    }

}
