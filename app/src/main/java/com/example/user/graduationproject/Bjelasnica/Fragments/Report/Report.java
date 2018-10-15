package com.example.user.graduationproject.Bjelasnica.Fragments.Report;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Report extends Fragment{
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ImageReportAdapter mAdapter;
    private List<Upload> mUploads = new ArrayList<>();
    private InternetConnection connection = new InternetConnection();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_report3, container, false);
        onReportClick(v);
        final ProgressBar mProgressCircle = v.findViewById(R.id.progress_circle);
        if(connection.getInternetConnection() == true){
            buildRecyclerView(v);
            firebaseHolder.getDatabaseReferenceForReport().addValueEventListener(valueEventListener(mAdapter, mProgressCircle, mUploads));
        }
        else{
            Toast.makeText(getActivity(), "Please connect to the internet", Toast.LENGTH_SHORT).show();
            loadUserReportPreferences();
            buildRecyclerView(v);
            mProgressCircle.setVisibility(View.INVISIBLE);
        }
        return v;
    }

    private void buildRecyclerView(View v){
        mAdapter = new ImageReportAdapter(getContext(), mUploads);
        mRecyclerView = v.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setReverseLayout(true);
        mLayoutManager.setStackFromEnd(true);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void saveUserReportPreferences(List<Upload> uploads){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share preference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(uploads);
        editor.putString("task list", json);
        editor.apply();
        int a = 0;
    }

    private void loadUserReportPreferences(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("share preference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<Upload>>(){}.getType();
        mUploads = gson.fromJson(json, type);
    }

    private ValueEventListener valueEventListener(final ImageReportAdapter mAdapter,
                                                  final ProgressBar mProgressCircle,
                                                  final List<Upload> mUploads) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUploads.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    final Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                    try{
                        saveUserReportPreferences(mUploads);
                    }catch (Exception e){

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
        v.findViewById(R.id.reportBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(getActivity(), PopUp.class);
                startActivity(intent);
            }
        });
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immage = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        //Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}


