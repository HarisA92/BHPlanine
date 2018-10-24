package com.example.user.graduationproject.Bjelasnica;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.user.graduationproject.Bjelasnica.Utils.Mountain;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResort;
import com.example.user.graduationproject.Bjelasnica.Utils.SkiResortHolder;
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

import java.util.ArrayList;

public class Home extends AppCompatActivity{
    private static final String SARAJEVO = "Sarajevo";
    private static final String JAHORINA = "Jahorina";
    private static String JAHORINA_TEXT = "Jahorina je planina u Bosni i Hercegovini koja pripada Dinarskom planinskom sustavu. Najviši vrh je Ogorjelica sa 1.916 m nadmorske visine. Ljeti je prekrivena gustom zelenom travom, a zimi i do 3 m visokim snijegom. Izvanredna konfiguracija terena, obilje vrlo kvalitetnog snijega, pogodna klima, 20 kilometara staza za alpske discipline kao i blage padine (Rajska dolina) uvrstile su ovu planinu među najljepše i najpoznatije ski-centre.";
    private static String BJELASNICA_WEB_CAMS = "Bjelasnica_livestream";
    private static String JAHORINA_WEB_CAMS = "Jahorina_livestream";
    private static String BJELASNICA_CJENOVNIK = "Bjelasnica_cjenovnik";
    private static String JAHORINA_CJENOVNIK = "Jahorina_cjenovnik";
    private static String BJELASNICA_GALLERY = "Bjelasnica_Gallery";
    private static String JAHORINA_GALLERY = "Jahorina_Gallery";
    private static String BJELASNICA_TRAIL_MAP = "Bjelasnica_trail_map";
    private static String JAHORINA_TRAIL_MAP = "Jahorina_trail_map";
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseHolder firebaseHolder = new FirebaseHolder();
    private AllMountainInformationHolder holder = new AllMountainInformationHolder();
    private TextView bjelasnica_base_cm, bjelasnica_lifts_open, bjelasnica_trails_open, bjelasnica_snowfall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createBjelasnicaMountainInfo();
        firebaseHolder.getDatabseReferenceForMountainInformation().addValueEventListener(valueEventListener());

        Button MyButton = findViewById(R.id.bjelasnica);
        MyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.BJELASNICA, SARAJEVO, BJELASNICA_WEB_CAMS, BJELASNICA_CJENOVNIK, BJELASNICA_GALLERY, BJELASNICA_TRAIL_MAP));
                startActivity(i);
            }
        });
        Button jahorinaButton = findViewById(R.id.jahorina);
        jahorinaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Main.class);
                SkiResortHolder.setSkiResort(new SkiResort(Mountain.JAHORINA, JAHORINA, JAHORINA_WEB_CAMS, JAHORINA_CJENOVNIK, JAHORINA_GALLERY, JAHORINA_TRAIL_MAP));
                startActivity(i);
            }
        });

        TextView textJahorina = findViewById(R.id.jahorinaText);
        textJahorina.setText(JAHORINA_TEXT);

        setupFirebaseListener();
    }

    private void createBjelasnicaMountainInfo(){
        bjelasnica_base_cm = findViewById(R.id.base_cm);
        bjelasnica_snowfall = findViewById(R.id.snowfall_cm);
        bjelasnica_lifts_open = findViewById(R.id.lifts_number);
        bjelasnica_trails_open = findViewById(R.id.trails_opened);
    }

    private ValueEventListener valueEventListener (){
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AllMountainInformationHolder getValues = dataSnapshot.getValue(AllMountainInformationHolder.class);
                ArrayList<AllMountainInformationHolder> arrayList = new ArrayList<>();
                arrayList.add(getValues);
                String depthBjelasnica =  arrayList.get(0).getBjelasnica_base_depth();
                String liftsBjelasnica = arrayList.get(0).getBjelasnica_lifts_open();
                String snowfallBjelasnica = arrayList.get(0).getBjelasnica_recent_snowfall();
                String trailsBjelasnica = arrayList.get(0).getBjelasnica_trails_open();
                bjelasnica_base_cm.setText(depthBjelasnica);
                bjelasnica_lifts_open.setText(liftsBjelasnica);
                bjelasnica_snowfall.setText(snowfallBjelasnica);
                bjelasnica_trails_open.setText(trailsBjelasnica);

                int a = 0;

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void setupFirebaseListener(){
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    //do nothing
                }
                else{
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
        if(mAuthStateListener != null){
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
        if(item.getItemId() == R.id.action_bar){
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
        }else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public boolean isLoggedInOnFacebook() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }
}
