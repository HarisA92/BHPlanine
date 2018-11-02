package com.example.user.graduationproject.Bjelasnica.Fragments.Report;

import android.content.Context;
import android.content.Intent;
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

import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.utils.Upload;
import com.example.user.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Report extends Fragment {

    private FirebaseHolder firebaseHolder = new FirebaseHolder(getActivity());
    private ImageReportAdapter mAdapter;
    private ArrayList<Upload> mUploads = new ArrayList<>();
    private InternetConnection connection = new InternetConnection();
    private String getMountain;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report3, container, false);
        onReportClick(v);

        getMountain = String.valueOf(getActivity().getTitle());

        final ProgressBar mProgressCircle = v.findViewById(R.id.progress_circle);
        if (connection.getInternetConnection()) {
            buildRecyclerView(v);
            firebaseHolder.getDatabaseReferenceForReport().addValueEventListener(valueEventListener(mAdapter, mProgressCircle, mUploads));
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            try {
                loadUserReportPreferences();
                buildRecyclerView(v);
            } catch (java.lang.NullPointerException ignored) {
            }
            mProgressCircle.setVisibility(View.INVISIBLE);
        }
        return v;
    }


    private void buildRecyclerView(View v) {
        mAdapter = new ImageReportAdapter(getContext(), mUploads);
        RecyclerView mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void saveUserReportPreferences(ArrayList<Upload> uploads) {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getMountain + getResources().getString(R.string.sharedPreferencesReport), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(uploads);
            editor.putString(getMountain + getResources().getString(R.string.sharedPreferencesReport_list), json);
            editor.apply();
        }
    }

    private void loadUserReportPreferences() {
        if (getActivity() != null) {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getMountain + getResources().getString(R.string.sharedPreferencesReport), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(getMountain + getResources().getString(R.string.sharedPreferencesReport_list), null);
            Type type = new TypeToken<ArrayList<Upload>>() {
            }.getType();
            mUploads = gson.fromJson(json, type);
            int a = 0;
        }
    }

    private ValueEventListener valueEventListener(final ImageReportAdapter mAdapter,
                                                  final ProgressBar mProgressCircle,
                                                  final ArrayList<Upload> mUploads) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                    try {
                        saveUserReportPreferences(mUploads);
                    } catch (Exception ignored) {
                    }
                }
                mAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void onReportClick(View v) {
        v.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), PopUp.class);
                startActivity(intent);
            }
        });
    }

}


