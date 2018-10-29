package com.example.user.graduationproject.Bjelasnica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.graduationproject.Bjelasnica.Adapters.HomeAdapter;
import com.example.user.graduationproject.Bjelasnica.Adapters.ImageReportAdapter;
import com.example.user.graduationproject.Bjelasnica.Firebase.FirebaseHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.AllMountainInformationHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.InternetConnection;
import com.example.user.graduationproject.Bjelasnica.Utils.Mountain;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResort;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
import com.example.user.graduationproject.Bjelasnica.Utils.Upload;
import com.example.user.graduationproject.R;
import com.facebook.AccessToken;
import com.facebook.all.All;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private InternetConnection internetConnection = new InternetConnection();
    private HomeAdapter homeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(internetConnection.getInternetConnection()){
            buildRecyclerView();
            firebaseHolder.getDatabseReferenceForMountainInformation().orderByKey().addValueEventListener(valueEventListener(homeAdapter, arrayList));
        }
        else{
            Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
            try{
                loadUserReportPreferences();
                buildRecyclerView();
            }catch(Exception ignored){}
        }

        setupFirebaseListener();
    }

    private ValueEventListener valueEventListener(final HomeAdapter adapter,
                                                  final ArrayList<AllMountainInformationHolder> list) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    AllMountainInformationHolder getValues = postSnapshot.getValue(AllMountainInformationHolder.class);
                    list.add(getValues);
                    try{
                        saveUserReportPreferences(list);
                    }catch (Exception ignored){}
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        
    }

    private void saveUserReportPreferences(ArrayList<AllMountainInformationHolder> allMountainInformationHolders){
        SharedPreferences sharedPreferences = this.getSharedPreferences("sharepreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allMountainInformationHolders);
        editor.putString("planine", json);
        editor.apply();
    }

    private void loadUserReportPreferences(){
        SharedPreferences sharedPreferences = this.getSharedPreferences("sharepreference", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("planine", null);
        Type type = new TypeToken<ArrayList<AllMountainInformationHolder>>(){}.getType();
        arrayList = gson.fromJson(json, type);
        int a = 0;
    }

    private void buildRecyclerView(){
        homeAdapter = new HomeAdapter(this, arrayList);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view_home);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(homeAdapter);
    }

    private void setupFirebaseListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                } else {
                    Toast.makeText(Home.this, "Signed out!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Home.this, WelcomeScreen.class);
                    startActivity(intent);
                }
            }
        };
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
        if (item.getItemId() == R.id.action_bar) {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
