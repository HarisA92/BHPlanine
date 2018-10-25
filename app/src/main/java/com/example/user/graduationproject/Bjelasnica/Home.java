package com.example.user.graduationproject.Bjelasnica;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String SARAJEVO = "Sarajevo";
    private static final String JAHORINA = "Jahorina";
    private static final String PALE = "Pale";
    private static final String IGMAN = "Hadzici";
    private static final String VLASIC = "Travnik";
    private static String BJELASNICA_WEB_CAMS = "Bjelasnica_livestream";
    private static String BJELASNICA_CJENOVNIK = "Bjelasnica_cjenovnik";
    private static String BJELASNICA_GALLERY = "Bjelasnica_Gallery";
    private static String BJELASNICA_TRAIL_MAP = "Bjelasnica_trail_map";
    private static String JAHORINA_WEB_CAMS = "Jahorina_livestream";
    private static String JAHORINA_CJENOVNIK = "Jahorina_cjenovnik";
    private static String JAHORINA_GALLERY = "Jahorina_Gallery";
    private static String JAHORINA_TRAIL_MAP = "Jahorina_trail_map";
    private static String RAVNAPLANINA_WEB_CAMS = "RavnaPlanina_livestream";
    private static String RAVNAPLANINA_CJENOVNIK = "RavnaPlanina_cjenovnik";
    private static String RAVNAPLANINA_GALLERY = "RavnaPlanina_Gallery";
    private static String RAVNAPLANINA_TRAIL_MAP = "RavnaPlanina_trail_map";
    private static String VLASIC_WEB_CAMS = "Vlasic_livestream";
    private static String VLASIC_CJENOVNIK = "Vlasic_cjenovnik";
    private static String VLASIC_GALLERY = "Vlasic_Gallery";
    private static String VLASIC_TRAIL_MAP = "Vlasic_trail_map";
    private static String IGMAN_WEB_CAMS = "Igman_livestream";
    private static String IGMAN_CJENOVNIK = "Igman_cjenovnik";
    private static String IGMAN_GALLERY = "Igman_Gallery";
    private static String IGMAN_TRAIL_MAP = "Igman_trail_map";
    private ArrayList<AllMountainInformationHolder> arrayList = new ArrayList<>();
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private InternetConnection internetConnection = new InternetConnection();
    private TextView bjelasnica_base_cm, bjelasnica_lifts_open, bjelasnica_trails_open, bjelasnica_snowfall;
    private TextView jahorina_base_cm, jahorina_lifts_open, jahorina_trails_open, jahorina_snowfall;
    private TextView ravnaplanina_base_cm, ravnaplanina_lifts_open, ravnaplanina_trails_open, ravnaplanina_snowfall;
    private TextView igman_base_cm, igman_lifts_open, igman_trails_open, igman_snowfall;
    private TextView vlasic_base_cm, vlasic_lifts_open, vlasic_trails_open, vlasic_snowfall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        createBjelasnicaMountainInfo();
        createJahorinaMountainInfo();
        createRavnaPlaninaMountainInfo();
        createVlasicMountainInfo();
        createIgmanMountainInfo();

        if(internetConnection.getInternetConnection() == true){
            firebaseHolder.getDatabseReferenceForMountainInformation().addValueEventListener(valueEventListener());
        }
        else{
            Toast.makeText(this, "Please connect to the internet", Toast.LENGTH_SHORT).show();
            try{
                loadUserReportPreferences();
            }catch(Exception e){}
        }
        Button MyButton = findViewById(R.id.bjelasnica);
        MyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.BJELASNICA, SARAJEVO, BJELASNICA_WEB_CAMS, BJELASNICA_CJENOVNIK, BJELASNICA_GALLERY, BJELASNICA_TRAIL_MAP));
                startActivity(intent);
            }
        });
        Button jahorinaButton = findViewById(R.id.jahorina);
        jahorinaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.JAHORINA, JAHORINA, JAHORINA_WEB_CAMS, JAHORINA_CJENOVNIK, JAHORINA_GALLERY, JAHORINA_TRAIL_MAP));
                startActivity(intent);
            }
        });

        Button ravnaplaninaButton = findViewById(R.id.ravnaplanina);
        ravnaplaninaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.RAVNAPLANINA, PALE, RAVNAPLANINA_WEB_CAMS, RAVNAPLANINA_CJENOVNIK, RAVNAPLANINA_GALLERY, RAVNAPLANINA_TRAIL_MAP));
                startActivity(intent);
            }
        });

        Button vlasicButton = findViewById(R.id.vlasic);
        vlasicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.VLASIC, VLASIC, VLASIC_WEB_CAMS, VLASIC_CJENOVNIK, VLASIC_GALLERY, VLASIC_TRAIL_MAP));
                startActivity(intent);
            }
        });

        Button igmanButton = findViewById(R.id.igman);
        igmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.IGMAN, IGMAN, IGMAN_WEB_CAMS, IGMAN_CJENOVNIK, IGMAN_GALLERY, IGMAN_TRAIL_MAP));
                startActivity(intent);

            }
        });
        setupFirebaseListener();
    }

    private void createBjelasnicaMountainInfo() {
        bjelasnica_base_cm = findViewById(R.id.base_cm);
        bjelasnica_snowfall = findViewById(R.id.snowfall_cm);
        bjelasnica_lifts_open = findViewById(R.id.lifts_number);
        bjelasnica_trails_open = findViewById(R.id.trails_opened);
    }

    private void createJahorinaMountainInfo() {
        jahorina_base_cm = findViewById(R.id.base_cm_jahorina);
        jahorina_lifts_open = findViewById(R.id.lifts_number_jahorina);
        jahorina_trails_open = findViewById(R.id.trails_opened_jahorina);
        jahorina_snowfall = findViewById(R.id.snowfall_cm_jahorina);
    }

    private void createRavnaPlaninaMountainInfo() {
        ravnaplanina_base_cm = findViewById(R.id.base_cm_ravnaplanina);
        ravnaplanina_lifts_open = findViewById(R.id.lifts_number_ravnaplanina);
        ravnaplanina_trails_open = findViewById(R.id.trails_opened_ravnaplanina);
        ravnaplanina_snowfall = findViewById(R.id.snowfall_cm_ravnaplanina);
    }

    private void createVlasicMountainInfo() {
        vlasic_base_cm = findViewById(R.id.base_cm_vlasic);
        vlasic_lifts_open = findViewById(R.id.lifts_number_vlasic);
        vlasic_trails_open = findViewById(R.id.trails_opened_vlasic);
        vlasic_snowfall = findViewById(R.id.snowfall_cm_vlasic);
    }

    private void createIgmanMountainInfo() {
        igman_base_cm = findViewById(R.id.base_cm_igman);
        igman_lifts_open = findViewById(R.id.lifts_number_igman);
        igman_trails_open = findViewById(R.id.trails_opened_igman);
        igman_snowfall = findViewById(R.id.snowfall_cm_igman);
    }

    private ValueEventListener valueEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AllMountainInformationHolder getValues = dataSnapshot.getValue(AllMountainInformationHolder.class);
                arrayList.add(getValues);
                String depthBjelasnica = arrayList.get(0).getBjelasnica_base_depth();
                String liftsBjelasnica = arrayList.get(0).getBjelasnica_lifts_open();
                String snowfallBjelasnica = arrayList.get(0).getBjelasnica_recent_snowfall();
                String trailsBjelasnica = arrayList.get(0).getBjelasnica_trails_open();
                bjelasnica_base_cm.setText(depthBjelasnica);
                bjelasnica_lifts_open.setText(liftsBjelasnica);
                bjelasnica_snowfall.setText(snowfallBjelasnica);
                bjelasnica_trails_open.setText(trailsBjelasnica);

                String depthJahorina = arrayList.get(0).getJahorina_base_depth();
                String liftsJahorina = arrayList.get(0).getJahorina_lifts_open();
                String snowfallJahorina = arrayList.get(0).getJahorina_recent_snowfall();
                String trailsJahorina = arrayList.get(0).getJahorina_trails_open();
                jahorina_base_cm.setText(depthJahorina);
                jahorina_lifts_open.setText(liftsJahorina);
                jahorina_snowfall.setText(snowfallJahorina);
                jahorina_trails_open.setText(trailsJahorina);

                String depthRavnaPlanina = arrayList.get(0).getRavna_Planina_base_depth();
                String liftsRavnaPlanina = arrayList.get(0).getRavna_Planina_lifts_open();
                String snowfallRavnaPlanina = arrayList.get(0).getRavna_Planina_recent_snowfall();
                String trailsRavnaPlanina = arrayList.get(0).getRavna_Planina_trails_open();
                ravnaplanina_base_cm.setText(depthRavnaPlanina);
                ravnaplanina_lifts_open.setText(liftsRavnaPlanina);
                ravnaplanina_snowfall.setText(snowfallRavnaPlanina);
                ravnaplanina_trails_open.setText(trailsRavnaPlanina);

                String depthVlasic = arrayList.get(0).getVlasic_base_depth();
                String liftsVlasic = arrayList.get(0).getVlasic_lifts_open();
                String snowfallVlasic = arrayList.get(0).getVlasic_recent_snowfall();
                String trailsVlasic = arrayList.get(0).getVlasic_trails_open();
                vlasic_base_cm.setText(depthVlasic);
                vlasic_lifts_open.setText(liftsVlasic);
                vlasic_snowfall.setText(snowfallVlasic);
                vlasic_trails_open.setText(trailsVlasic);

                String depthIgman = arrayList.get(0).getIgman_base_depth();
                String liftsIgman = arrayList.get(0).getIgman_lifts_open();
                String snowfallIgman = arrayList.get(0).getIgman_recent_snowfall();
                String trailsIgman = arrayList.get(0).getIgman_trails_open();
                igman_base_cm.setText(depthIgman);
                igman_lifts_open.setText(liftsIgman);
                igman_snowfall.setText(snowfallIgman);
                igman_trails_open.setText(trailsIgman);

                try{
                    saveUserReportPreferences(arrayList);
                }catch (Exception e){}
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
        Type type = new TypeToken<ArrayList<Upload>>(){}.getType();
        arrayList = gson.fromJson(json, type);
    }


    private void setupFirebaseListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    //do nothing
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
        FirebaseAuth.getInstance().addAuthStateListener((FirebaseAuth.AuthStateListener) mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthStateListener != null) {
            FirebaseAuth.getInstance().removeAuthStateListener((FirebaseAuth.AuthStateListener) mAuthStateListener);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }
    public boolean isLoggedInOnFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
