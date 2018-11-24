package com.bhplanine.user.graduationproject.Bjelasnica.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bhplanine.user.graduationproject.Bjelasnica.adapters.HomeAdapter;
import com.bhplanine.user.graduationproject.Bjelasnica.firebase.FirebaseHolder;
import com.bhplanine.user.graduationproject.Bjelasnica.models.AllMountainInformationHolder;
import com.bhplanine.user.graduationproject.Bjelasnica.utils.InternetConnection;
import com.bhplanine.user.graduationproject.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    private ArrayList<AllMountainInformationHolder> arrayList = new ArrayList<>();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseHolder firebaseHolder = new FirebaseHolder(this);
    private InternetConnection internetConnection = new InternetConnection();
    private HomeAdapter homeAdapter;
    private ProgressBar mProgressCircle;
    private RecyclerView mRecyclerView;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buildRecyclerView();
        mProgressCircle = findViewById(R.id.progress_circle);
        floatingActionButton = findViewById(R.id.fab);
        sendEmail();

        if (internetConnection.getInternetConnection()) {
            firebaseHolder.getDatabseReferenceForMountainInformation().orderByKey().addValueEventListener(valueEventListener());
        } else {
            loadUserReportPreferences();
            buildRecyclerAdapter();
            mProgressCircle.setVisibility(View.INVISIBLE);
        }
        setupFirebaseListener();
    }

    private ValueEventListener valueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    AllMountainInformationHolder getValues = postSnapshot.getValue(AllMountainInformationHolder.class);
                    arrayList.add(getValues);
                }
                saveUserReportPreferences(arrayList);
                buildRecyclerAdapter();
                homeAdapter.notifyDataSetChanged();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    private void buildRecyclerAdapter() {
        homeAdapter = new HomeAdapter(this, arrayList);
        mRecyclerView.setAdapter(homeAdapter);
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view_home);
        mRecyclerView.setNestedScrollingEnabled(false);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void saveUserReportPreferences(ArrayList<AllMountainInformationHolder> allMountainInformationHolders) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.sharedPreferencesHome), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allMountainInformationHolders);
        editor.putString(getResources().getString(R.string.sharedPreferencesHome_list), json);
        editor.apply();
    }

    private void loadUserReportPreferences() {
        SharedPreferences sharedPreferences = this.getSharedPreferences(getResources().getString(R.string.sharedPreferencesHome), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(getResources().getString(R.string.sharedPreferencesHome_list), null);
        Type type = new TypeToken<ArrayList<AllMountainInformationHolder>>() {
        }.getType();
        arrayList = gson.fromJson(json, type);
    }

    private void sendEmail() {
        floatingActionButton.setOnClickListener(view -> {
            if (internetConnection.getInternetConnection()) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "bhplaninesupp@gmail.com"));
                startActivity(intent);
            } else {
                Toast.makeText(this, getResources().getString(R.string.connect_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupFirebaseListener() {
        mAuthStateListener = firebaseAuth -> {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            if (firebaseUser != null) {
            } else {
                Toast.makeText(Home.this, getResources().getString(R.string.Sign_out), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Home.this, WelcomeScreen.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserReportPreferences();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.log_out) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
